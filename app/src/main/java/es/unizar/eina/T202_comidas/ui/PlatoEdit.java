package es.unizar.eina.T202_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.T202_comidas.R;
import es.unizar.eina.T202_comidas.database.Plato;

/** Pantalla utilizada para la creación o edición de un plato */
public class PlatoEdit extends AppCompatActivity {

    public static final String PLATO_ID = "id";
    public static final String PLATO_NOMBRE = "plato nombre";
    public static final Plato.Categoria PLATO_CATEGORIA = Plato.Categoria.PRIMERO;
    public static final String PLATO_DESCRIPCION = "plato desc";

    private EditText mTitleText;

    private EditText mBodyText;

    private Integer mRowId;

    Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidoedit);

        mTitleText = findViewById(R.id.title);
        mBodyText = findViewById(R.id.body);

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mTitleText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(PlatoEdit.PLATO_NOMBRE, mTitleText.getText().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_DESCRIPCION, mBodyText.getText().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(PlatoEdit.PLATO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        populateFields();

    }

    /** Rellena los datos del plato */
    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mTitleText.setText(extras.getString(PlatoEdit.PLATO_NOMBRE));
            mBodyText.setText(extras.getString(PlatoEdit.PLATO_DESCRIPCION));
            mRowId = extras.getInt(PlatoEdit.PLATO_ID);
        }
    }

}
