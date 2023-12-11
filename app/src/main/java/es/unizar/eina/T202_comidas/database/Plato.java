package es.unizar.eina.T202_comidas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa un plato y que consta de nombre, categoría y descripción */
@Entity(tableName = "plato")
public class Plato {

    public enum Categoria {
        PRIMERO, SEGUNDO, POSTRE
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "categoria")
    private Categoria categoria;

    @NonNull
    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @NonNull
    @ColumnInfo(name = "precio")
    private double precio;

    public Plato(@NonNull String nombre, @NonNull Categoria categoria, @NonNull String descripcion, @NonNull double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    /** Devuelve el identificador del plato */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador de un plato */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve el nombre del plato */
    public String getNombre(){
        return this.nombre;
    }

    /** Devuelve la categoría del plato */
    public Categoria getCategoria(){
        return this.categoria;
    }

    /** Devuelve la descripcion del plato */
    public String getDescripcion(){
        return this.descripcion;
    }

    /** Devuelve el precio del plato */
    public double getPrecio(){
        return this.precio;
    }

}
