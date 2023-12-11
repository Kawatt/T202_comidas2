package es.unizar.eina.T202_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.T202_comidas.R;
import es.unizar.eina.T202_comidas.database.Pedido;

/** Pantalla con la lista de Pedidos */
public class Pedidos extends AppCompatActivity {
    private PedidoViewModel mPedidoViewModel;

    public static final int ACTIVITY_CREATE = 1;

    public static final int ACTIVITY_EDIT = 2;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    PedidoListAdapter mAdapter;

    FloatingActionButton mFab;

    Button mPedidosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PedidoListAdapter(new PedidoListAdapter.PedidoDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);

        mPedidoViewModel.getAllPedidos().observe(this, pedidos -> {
            // Update the cached copy of the pedidos in the adapter.
            mAdapter.submitList(pedidos);
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(view -> {
            createPedido();
        });

        mPedidosButton = findViewById(R.id.platosboton);
        mPedidosButton.setOnClickListener(view -> {
            abrirPlatos();
        });

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_pedido);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createPedido();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();

        if (resultCode != RESULT_OK) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        } else {

            switch (requestCode) {
                case ACTIVITY_CREATE:
                    Pedido newPedido = new Pedido(Pedido.Estado.SOLICITADO, extras.getString(PedidoEdit.PEDIDO_CLIENTE),
                            extras.getInt(PedidoEdit.PEDIDO_NUMTELEFONO), 0,
                            0, 0);
                    mPedidoViewModel.insert(newPedido);
                    break;
                case ACTIVITY_EDIT:
                    int id = extras.getInt(PedidoEdit.PEDIDO_ID);
                    Pedido updatedPedido = new Pedido(Pedido.Estado.SOLICITADO, extras.getString(PedidoEdit.PEDIDO_CLIENTE),
                            extras.getInt(PedidoEdit.PEDIDO_NUMTELEFONO), 0,
                            0, 0);
                    updatedPedido.setId(id);
                    mPedidoViewModel.update(updatedPedido);
                    break;
            }
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        Pedido current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Deleting " + current.getCliente(),
                        Toast.LENGTH_LONG).show();
                mPedidoViewModel.delete(current);
                return true;
            case EDIT_ID:
                editPedido(current);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /** Viaja a la pantalla de edición de pedidos */
    private void createPedido() {
        Intent intent = new Intent(this, PedidoEdit.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    /** Edita un pedido */
    private void editPedido(Pedido current) {
        Intent intent = new Intent(this, PedidoEdit.class);
        intent.putExtra(PedidoEdit.PEDIDO_CLIENTE, current.getCliente());
        intent.putExtra(PedidoEdit.PEDIDO_CLIENTE, current.getCliente());
        intent.putExtra(PedidoEdit.PEDIDO_ID, current.getId());
        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    /** Viaja a la pantalla de platos */
    private void abrirPlatos() {
        Intent intent = new Intent(this, Platos.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

}