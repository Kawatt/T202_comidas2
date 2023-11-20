package es.unizar.eina.T202_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa un pedido y que consta de un estado, cliente, numero de telefono, fecha y hora de recogida, numero de raciones, lista de platos y precio */
@Entity(tableName = "pedido")
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "body")
    private String body;

    public Pedido(@NonNull String title, String body) {
        this.title = title;
        this.body = body;
    }

    /** Devuelve el identificador del pedido */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador de un pedido */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve el título del pedido */
    public String getTitle(){
        return this.title;
    }

    /** Devuelve el cuerpo del pedido */
    public String getBody(){
        return this.body;
    }

}
