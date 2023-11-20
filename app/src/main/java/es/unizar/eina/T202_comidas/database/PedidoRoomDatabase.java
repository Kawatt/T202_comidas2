package es.unizar.eina.T202_comidas.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pedido.class}, version = 1, exportSchema = false)
public abstract class PedidoRoomDatabase extends RoomDatabase {

    public abstract PedidoDao pedidoDao();

    private static volatile PedidoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PedidoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PedidoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PedidoRoomDatabase.class, "pedido_database")
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
                PedidoDao dao = INSTANCE.pedidoDao();
                dao.deleteAll();

                Pedido pedido = new Pedido("Pedido 1", "Pedido 1's body");
                dao.insert(pedido);
                pedido = new Pedido("Pedido 2", "Pedido 2's body");
                dao.insert(pedido);
            });
        }
    };

}
