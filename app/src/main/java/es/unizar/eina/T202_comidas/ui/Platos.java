package es.unizar.eina.T202_comidas.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.T202_comidas.database.Plato;
import es.unizar.eina.T202_comidas.R;

/** Pantalla con la lista de Platos */
public class Platos extends AppCompatActivity {
    private PlatoViewModel mPlatoViewModel;

    public static final int ACTIVITY_CREATE = 1;

    public static final int ACTIVITY_EDIT = 2;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    PlatoListAdapter mAdapter;

    FloatingActionButton mFab;
    Button mPedidosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Abre la pantalla:
        setContentView(R.layout.activity_platos);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PlatoListAdapter(new PlatoListAdapter.PlatoDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);

        mPlatoViewModel.getAllPlatos().observe(this, platos -> {
            // Update the cached copy of the platos in the adapter.
            mAdapter.submitList(platos);
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(view -> {
            createPlato();
        });

        mPedidosButton = findViewById(R.id.pedidosboton);
        mPedidosButton.setOnClickListener(view -> {
            abrirPedidos();
        });

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_plato);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createPlato();
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
                    Plato newPlato = new Plato(extras.getString(PlatoEdit.PLATO_TITLE)
                            , extras.getString(PlatoEdit.PLATO_BODY));
                    mPlatoViewModel.insert(newPlato);
                    break;
                case ACTIVITY_EDIT:

                    int id = extras.getInt(PlatoEdit.PLATO_ID);
                    Plato updatedPlato = new Plato(extras.getString(PlatoEdit.PLATO_TITLE)
                            , extras.getString(PlatoEdit.PLATO_BODY));
                    updatedPlato.setId(id);
                    mPlatoViewModel.update(updatedPlato);
                    break;
            }
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        Plato current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Deleting " + current.getTitle(),
                        Toast.LENGTH_LONG).show();
                mPlatoViewModel.delete(current);
                return true;
            case EDIT_ID:
                editPlato(current);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /** Viaja a la pantalla de edición de platos */
    private void createPlato() {
        Intent intent = new Intent(this, PlatoEdit.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    /** Edita un plato */
    private void editPlato(Plato current) {
        Intent intent = new Intent(this, PlatoEdit.class);
        intent.putExtra(PlatoEdit.PLATO_TITLE, current.getTitle());
        intent.putExtra(PlatoEdit.PLATO_BODY, current.getBody());
        intent.putExtra(PlatoEdit.PLATO_ID, current.getId());
        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    /** Viaja a la pantalla de pedidos */
    private void abrirPedidos() {
        Intent intent = new Intent(this, Pedidos.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

}