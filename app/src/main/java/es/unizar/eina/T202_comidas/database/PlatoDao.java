package es.unizar.eina.T202_comidas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definición de un Data Access Object para los Platos */
@Dao
public interface PlatoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Plato plato);

    @Update
    int update(Plato plato);

    @Delete
    int delete(Plato plato);

    @Query("DELETE FROM Plato")
    void deleteAll();

    @Query("SELECT * FROM Plato ORDER BY nombre ASC")
    LiveData<List<Plato>> getOrderedPlatos();
}

