package es.unizar.eina.T202_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Set;

/** Clase anotada como entidad que representa un pedido y que consta de un estado, cliente, numero de telefono, fecha y hora de recogida, numero de raciones, lista de platos y precio */
@Entity(tableName = "pedido")
public class Pedido {

    public enum Estado {
        SOLICITADO, PREPARADO, RECOGIDO
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "estado")
    private Estado estado;

    @NonNull
    @ColumnInfo(name = "cliente")
    private String cliente;

    @NonNull
    @ColumnInfo(name = "numero_telefono")
    private int numTelefono;

    @NonNull
    @ColumnInfo(name = "tiempo_recogida")
    private long tiempoRecogida;

    @NonNull
    @ColumnInfo(name = "raciones")
    private int numRaciones;

    //@ColumnInfo(name = "platos")
    //private int platos;
    //private Set<Plato> platos;
    @NonNull
    @ColumnInfo(name = "precioTotal")
    private double precioTotal;

    public Pedido(Estado estado, @NonNull String cliente, @NonNull int numTelefono, @NonNull long tiempoRecogida, @NonNull int numRaciones, @NonNull double precioTotal) {
        this.estado = estado;
        this.cliente = cliente;
        this.numTelefono = numTelefono;
        this.tiempoRecogida = tiempoRecogida;
        this.numRaciones = numRaciones;
        this.precioTotal = precioTotal;
    }

    /** Devuelve el identificador del pedido */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador de un pedido */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve el estado del pedido */
    public Estado getEstado(){
        return this.estado;
    }

    /** Devuelve el cliente del pedido */
    public String getCliente(){
        return this.cliente;
    }

    /** Devuelve el numero de telefono del pedido */
    public int getNumTelefono(){
        return this.numTelefono;
    }

    /** Devuelve la fecha y hora de recogida del pedido */
    public long getTiempoRecogida(){
        return this.tiempoRecogida;
    }

    /** Devuelve el numero de raciones del pedido */
    public int getNumRaciones(){
        return this.numRaciones;
    }

    /** Devuelve los platos del pedido */
    //public int getPlatos(){
        //return this.platos;
    //}

    /** Devuelve el precio del pedido */
    public double getPrecioTotal(){
        return this.precioTotal;
    }

}
