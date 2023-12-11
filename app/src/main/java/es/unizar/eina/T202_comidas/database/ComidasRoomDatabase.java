package es.unizar.eina.T202_comidas.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pedido.class, Plato.class}, version = 1, exportSchema = false)
public abstract class ComidasRoomDatabase extends RoomDatabase {

    public abstract PedidoDao pedidoDao();
    public abstract PlatoDao platoDao();

    private static volatile ComidasRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ComidasRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ComidasRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ComidasRoomDatabase.class, "comida_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more notes, just add them.
                PlatoDao platodao = INSTANCE.platoDao();
                PedidoDao pedidodao = INSTANCE.pedidoDao();
                platodao.deleteAll();
                pedidodao.deleteAll();

                Plato plato = new Plato("Espagueti", Plato.Categoria.PRIMERO, "Espagueti a la carbonara", 25);
                platodao.insert(plato);
                plato = new Plato("Pescado", Plato.Categoria.SEGUNDO, "Filete de pescado", 10);
                platodao.insert(plato);
                plato = new Plato("Fondue de fresas", Plato.Categoria.POSTRE, "Chocolate fundido con fresas", 5.66);
                platodao.insert(plato);
                Pedido pedido = new Pedido(Pedido.Estado.SOLICITADO, "Cliente 1", 111111111, 1 /*new TiempoRecogida(1, 1, 1, 1, 1)*/, 0, 1);
                pedidodao.insert(pedido);
                pedido = new Pedido(Pedido.Estado.SOLICITADO, "Cliente 2", 222222222, 1 /*new TiempoRecogida(1, 1, 1, 1, 1)*/, 0 ,1 );
                pedidodao.insert(pedido);
            });
        }
    };

}
