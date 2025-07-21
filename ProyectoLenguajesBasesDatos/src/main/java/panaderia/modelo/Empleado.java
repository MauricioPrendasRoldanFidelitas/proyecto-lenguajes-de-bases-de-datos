package panaderia.modelo;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String puesto;
    private double salario;

    public Empleado() {}

    public Empleado(int idEmpleado, String nombre, String puesto, double salario) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
