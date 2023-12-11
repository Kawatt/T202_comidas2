package es.unizar.eina.T202_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.T202_comidas.R;
import es.unizar.eina.T202_comidas.database.Plato;

/** Pantalla utilizada para la creación o edición de un plato */
public class PlatoEdit extends AppCompatActivity {

    public static final String PLATO_ID = "id";
    public static final String PLATO_NOMBRE = "nombre";
    public static final String PLATO_CATEGORIA = "categoria";
    public static final String PLATO_DESCRIPCION = "descripcion";
    public static final String PLATO_PRECIO = "precio";

    private EditText mNameText;
    private Spinner mCategorySpinner;
    private EditText mDescText;
    private EditText mPriceText;
    private Button mSaveButton;

    private Integer mRowId;

    private String categorySelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoedit);

        initComponents();
        mSaveButton.setOnClickListener(view -> {
            savePlato();
        });

        populateFields();

    }

    private void savePlato() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mNameText.getText()) || TextUtils.isEmpty(mPriceText.getText()) || (categorySelected == "---")) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            replyIntent.putExtra(PlatoEdit.PLATO_NOMBRE, mNameText.getText().toString());
            replyIntent.putExtra(PlatoEdit.PLATO_DESCRIPCION, mDescText.getText().toString());
            replyIntent.putExtra(PlatoEdit.PLATO_CATEGORIA, Plato.Categoria.POSTRE/*textToCategory()*/);
            replyIntent.putExtra(PlatoEdit.PLATO_PRECIO, /*0.00*/Double.parseDouble(mPriceText.getText().toString()));
            if (mRowId!=null) {
                replyIntent.putExtra(PlatoEdit.PLATO_ID, mRowId.intValue());
            }
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

    private Plato.Categoria textToCategory() {
        switch (categorySelected) {
            case "Primero": return Plato.Categoria.PRIMERO;
            case "Segundo": return Plato.Categoria.SEGUNDO;
            default: return Plato.Categoria.POSTRE;
        }
    }

    private void initComponents() {
        mNameText = findViewById(R.id.name);
        mCategorySpinner = findViewById(R.id.category);
        mDescText = findViewById(R.id.description);
        mPriceText = findViewById(R.id.price);
        mSaveButton = findViewById(R.id.button_save);

        initCategory();
    }

    private void initCategory() {
        String[] opciones = {"---","Primero", "Segundo", "Postre"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(adaptador);

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String categorySelected = opciones[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected
            }
        });
    }

    /** Rellena los datos del plato */
    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mNameText.setText(extras.getString(PlatoEdit.PLATO_NOMBRE));
            mDescText.setText(extras.getString(PlatoEdit.PLATO_DESCRIPCION));
            mPriceText.setText(extras.getString(PlatoEdit.PLATO_PRECIO));
            Plato.Categoria cat = (Plato.Categoria)extras.getSerializable(PlatoEdit.PLATO_CATEGORIA);
            mCategorySpinner.setSelection(cat.ordinal()+1);
            mRowId = extras.getInt(PlatoEdit.PLATO_ID);
        }
    }

}
