package es.unizar.eina.T202_comidas.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import es.unizar.eina.T202_comidas.R;

/** Pantalla utilizada para la creación o edición de un pedido */
public class PedidoEdit extends AppCompatActivity {

    public static final String PEDIDO_TITLE = "pedido title";
    public static final String PEDIDO_BODY = "body";
    public static final String PEDIDO_ID = "id";

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
                replyIntent.putExtra(PedidoEdit.PEDIDO_TITLE, mTitleText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_BODY, mBodyText.getText().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(PedidoEdit.PEDIDO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        populateFields();

    }

    /** Rellena los datos del pedido */
    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mTitleText.setText(extras.getString(PedidoEdit.PEDIDO_TITLE));
            mBodyText.setText(extras.getString(PedidoEdit.PEDIDO_BODY));
            mRowId = extras.getInt(PedidoEdit.PEDIDO_ID);
        }
    }

}
