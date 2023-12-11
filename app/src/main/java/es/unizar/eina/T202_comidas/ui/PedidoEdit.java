package es.unizar.eina.T202_comidas.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import es.unizar.eina.T202_comidas.R;
import es.unizar.eina.T202_comidas.database.Pedido;
import es.unizar.eina.T202_comidas.database.TiempoRecogida;

/** Pantalla utilizada para la creación o edición de un pedido */
public class PedidoEdit extends AppCompatActivity {
    public static final String PEDIDO_ID = "id";
    public static final String PEDIDO_ESTADO = "estado";
    public static final String PEDIDO_CLIENTE = "cliente";
    public static final String PEDIDO_NUMTELEFONO = "numtelefono";
    public static final String PEDIDO_TIEMPORECOGIDA = "tiempo_recogida";
    public static final String PEDIDO_NUMRACIONES = "numero_raciones";
    public static final String PEDIDO_PRECIO = "precio_total";

    private EditText mClientText;
    private EditText mNumTelefonoText;
    private Spinner mEstadoSpinner;
    private CalendarView mRecogidaCalendar;
    private TimePicker mRecogidaTime;

    private Integer mRowId;

    private Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pantalla a utilizar
        setContentView(R.layout.activity_pedidoedit);

        initComponents();

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mClientText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(PedidoEdit.PEDIDO_CLIENTE, mClientText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_NUMTELEFONO, Integer.parseInt(mNumTelefonoText.getText().toString()));
                if (mRowId!=null) {
                    replyIntent.putExtra(PedidoEdit.PEDIDO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        populateFields();

    }

    private void initComponents() {
        mClientText = findViewById(R.id.client);
        mEstadoSpinner = findViewById(R.id.estate);
        mNumTelefonoText = findViewById(R.id.telephone);
        mRecogidaCalendar = findViewById(R.id.pick_up_date);
        mRecogidaTime = findViewById(R.id.pick_up_hour);
        mSaveButton = findViewById(R.id.button_save);
        
        initDate();
        initHour();
    }

    private void initDate() {
        // Obtén la fecha actual
        Calendar calendar = Calendar.getInstance();
        long currentDate = calendar.getTimeInMillis();

        // Establece la fecha actual en el CalendarView
        mRecogidaCalendar.setDate(currentDate);

        // Configura un listener si es necesario
        mRecogidaCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Maneja el cambio de fecha si es necesario
                // year, month, dayOfMonth son la fecha seleccionada
            }
        });
    }

    private void initHour() {
        int horaActual = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        int minutoActual = java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE);

        // Establece la hora y el minuto actuales en el TimePicker
        mRecogidaTime.setHour(horaActual);
        mRecogidaTime.setMinute(minutoActual);
    }

    /** Rellena los datos del pedido */
    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mClientText.setText(extras.getString(PedidoEdit.PEDIDO_CLIENTE));
            Log.i("ETIQUETA","AQUI" + extras.getInt(PedidoEdit.PEDIDO_NUMTELEFONO));
            Log.i("ETIQUETA","AQUI" + extras.getInt(PedidoEdit.PEDIDO_NUMTELEFONO));
            mNumTelefonoText.setText(String.valueOf(extras.getInt(PedidoEdit.PEDIDO_NUMTELEFONO)));
            mRowId = extras.getInt(PedidoEdit.PEDIDO_ID);
        }
    }

}
