package es.unizar.eina.T202_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.T202_comidas.R;
import es.unizar.eina.T202_comidas.database.Note;

/** Pantalla principal de la aplicación Notepad */
public class Pedidos extends AppCompatActivity {
    private NoteViewModel mNoteViewModel;

    public static final int ACTIVITY_CREATE = 1;

    public static final int ACTIVITY_EDIT = 2;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    NoteListAdapter mAdapter;

    FloatingActionButton mFab;

    Button mPlatosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new NoteListAdapter(new NoteListAdapter.NoteDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        mNoteViewModel.getAllNotes().observe(this, notes -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.submitList(notes);
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(view -> {
            createNote();
        });

        mPlatosButton = findViewById(R.id.platosboton);
        mPlatosButton.setOnClickListener(view -> {
            abrirPlatos();
        });

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_note);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createNote();
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
                    Note newNote = new Note(extras.getString(PedidoEdit.PEDIDO_TITLE)
                            , extras.getString(PedidoEdit.PEDIDO_BODY));
                    mNoteViewModel.insert(newNote);
                    break;
                case ACTIVITY_EDIT:

                    int id = extras.getInt(PedidoEdit.PEDIDO_ID);
                    Note updatedNote = new Note(extras.getString(PedidoEdit.PEDIDO_TITLE)
                            , extras.getString(PedidoEdit.PEDIDO_BODY));
                    updatedNote.setId(id);
                    mNoteViewModel.update(updatedNote);
                    break;
            }
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        Note current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Deleting " + current.getTitle(),
                        Toast.LENGTH_LONG).show();
                mNoteViewModel.delete(current);
                return true;
            case EDIT_ID:
                editNote(current);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent intent = new Intent(this, PedidoEdit.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }


    private void editNote(Note current) {
        Intent intent = new Intent(this, PedidoEdit.class);
        intent.putExtra(PedidoEdit.PEDIDO_TITLE, current.getTitle());
        intent.putExtra(PedidoEdit.PEDIDO_BODY, current.getBody());
        intent.putExtra(PedidoEdit.PEDIDO_ID, current.getId());
        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    private void abrirPlatos() {
        Intent intent = new Intent(this, Platos.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

}