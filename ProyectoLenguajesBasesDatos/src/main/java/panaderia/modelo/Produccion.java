package panaderia.modelo;

import java.sql.Date;

public class Produccion {
    private int idProduccion;
    private int idProducto;
    private Date fecha;

    public Produccion() {}

    public Produccion(int idProduccion, int idProducto) {
        this.idProduccion = idProduccion;
        this.idProducto = idProducto;
    }

    public int getIdProduccion() {
        return idProduccion;
    }

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Produccion{" +
                "idProduccion=" + idProduccion +
                ", idProducto=" + idProducto +
                ", fecha=" + fecha +
                '}';
    }
}
