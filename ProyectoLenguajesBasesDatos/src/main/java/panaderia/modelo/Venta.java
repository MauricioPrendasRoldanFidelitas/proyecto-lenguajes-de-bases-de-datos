package panaderia.modelo;

import java.sql.Date;

public class Venta {
    private int idVenta;
    private Date fecha;

    public Venta() {
    }

    public Venta(int idVenta, Date fecha) {
        this.idVenta = idVenta;
        this.fecha = fecha;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", fecha=" + fecha +
                '}';
    }
}
