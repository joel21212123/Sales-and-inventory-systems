
package bodega_myc_.Modelo;

import java.sql.Timestamp;
public class ClaseCliente {
    
    private int id_clientes;
    private String nombre_cliente;
    private String dni;
    private String telefono;
     private int cantidadCompras;
    private double montoTotalGastado;
    private Timestamp fecha_registro;

    public ClaseCliente() {
        
    }

    public int getId_clientes() {
        return id_clientes;
    }

    public void setId_clientes(int id_clientes) {
        this.id_clientes = id_clientes;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
 public int getCantidadCompras() {
        return cantidadCompras;
    }

    public void setCantidadCompras(int cantidadCompras) {
        this.cantidadCompras = cantidadCompras;
    }

    public double getMontoTotalGastado() {
        return montoTotalGastado;
    }

    public void setMontoTotalGastado(double montoTotalGastado) {
        this.montoTotalGastado = montoTotalGastado;
    }
    
    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }    
    
}
