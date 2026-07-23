
package bodega_myc_.Modelo;

import java.sql.Timestamp;

public class ClaseCompraProducto {
     private int id_compras;
    private String nombre_proveedores;
    private String nombre_Productos;
    private double precio_adquisicion;
    private String categoria;
    private int cantidad;
    private Timestamp fecha_compras;
    private int usuario_id;

    public ClaseCompraProducto() {
    }

    public int getId_compras() {
        return id_compras;
    }

    public void setId_compras(int id_compras) {
        this.id_compras = id_compras;
    }

    public String getNombre_proveedores() {
        return nombre_proveedores;
    }

    public void setNombre_proveedores(String nombre_proveedores) {
        this.nombre_proveedores = nombre_proveedores;
    }

    public String getNombre_Productos() {
        return nombre_Productos;
    }

    public void setNombre_Productos(String nombre_Productos) {
        this.nombre_Productos = nombre_Productos;
    }

    public double getPrecio_adquisicion() {
        return precio_adquisicion;
    }

    public void setPrecio_adquisicion(double precio_adquisicion) {
        this.precio_adquisicion = precio_adquisicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Timestamp getFecha_compras() {
        return fecha_compras;
    }

    public void setFecha_compras(Timestamp fecha_compras) {
        this.fecha_compras = fecha_compras;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    
}
