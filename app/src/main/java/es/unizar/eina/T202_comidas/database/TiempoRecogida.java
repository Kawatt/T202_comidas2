package es.unizar.eina.T202_comidas.database;

public class TiempoRecogida implements Comparable<TiempoRecogida>{
    private int hora;
    private int minuto;
    private int dia;
    private int mes;
    private int anyo;

    public TiempoRecogida(int minuto, int hora, int dia, int mes, int anyo) {
        this.minuto = minuto;
        this.hora = hora;
        this.dia = dia;
        this.mes = mes;
        this.anyo = anyo;
    }

    @Override
    public int compareTo(TiempoRecogida obj) {
        if (this.anyo != obj.anyo) {
            return Integer.compare(this.anyo, obj.anyo);
        }
        if (this.mes != obj.mes) {
            return Integer.compare(this.mes, obj.mes);
        }
        if (this.dia != obj.dia) {
            return Integer.compare(this.dia, obj.dia);
        }
        if (this.hora != obj.hora) {
            return Integer.compare(this.hora, obj.hora);
        }
        return Integer.compare(this.minuto, obj.minuto);
    }

}
