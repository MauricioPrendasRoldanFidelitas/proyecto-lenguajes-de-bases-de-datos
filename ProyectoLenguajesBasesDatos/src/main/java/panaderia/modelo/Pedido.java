package panaderia.modelo;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private String fecha;

    public Pedido() {
    }

    public Pedido(int idPedido, int idCliente, String fecha) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", idCliente=" + idCliente +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
