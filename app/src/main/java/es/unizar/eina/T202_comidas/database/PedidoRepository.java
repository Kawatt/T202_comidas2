package es.unizar.eina.T202_comidas.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PedidoRepository {
    private PedidoDao mPedidoDao;
    private LiveData<List<Pedido>> mAllPedidos;

    // Note that in order to unit test the NoteRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PedidoRepository(Application application) {
        PedidoRoomDatabase db = PedidoRoomDatabase.getDatabase(application);
        mPedidoDao = db.pedidoDao();
        mAllPedidos = mPedidoDao.getOrderedPedidos();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Pedido>> getAllPedidos() {
        return mAllPedidos;
    }

    /** Inserta una nota
     * @param pedido
     * @return un valor entero largo con el identificador de la nota que se ha creado.
     */
    public long insert(Pedido pedido) {
        final long[] result = {0};
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        PedidoRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.insert(pedido);
        });
        return result[0];
    }

    /** Modifica una nota
     * @param pedido
     * @return un valor entero con el número de filas modificadas.
     */
    public int update(Pedido pedido) {
        final int[] result = {0};
        PedidoRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.update(pedido);
        });
        return result[0];
    }

    /** Elimina una nota
     * @param pedido
     * @return un valor entero con el número de filas eliminadas.
     */
    public int delete(Pedido pedido) {
        final int[] result = {0};
        PedidoRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.delete(pedido);
        });
        return result[0];
    }
}
