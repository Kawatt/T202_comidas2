package es.unizar.eina.T202_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa un plato y que consta de nombre, categoría y descripción */
@Entity(tableName = "plato")
public class Plato {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "body")
    private String body;

    public Plato(@NonNull String title, String body) {
        this.title = title;
        this.body = body;
    }

    /** Devuelve el identificador del plato */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador de un plato */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve el título del plato */
    public String getTitle(){
        return this.title;
    }

    /** Devuelve el cuerpo del plato */
    public String getBody(){
        return this.body;
    }

}
