
package bodega_myc_.Modelo;

import java.sql.Timestamp;

public class ClaseProducto {
    
    private int idproductos;
    private String nombre_Productos;
    private String descripcion;
    private double precio_adquisicion;
    private double precio_venta;
    private int stock;
    private int stock_minimo;
    private String categoria;
    private Timestamp fecha_creacion;
    private Timestamp fecha_actualizacion;
    private int cantidadVendida;
    private double montoTotalVendido;
    
    public ClaseProducto() {
        
    }

    public int getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(int idproductos) {
        this.idproductos = idproductos;
    }

    public String getNombre_Productos() {
        return nombre_Productos;
    }

    public void setNombre_Productos(String nombre_Productos) {
        this.nombre_Productos = nombre_Productos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio_adquisicion() {
        return precio_adquisicion;
    }

    public void setPrecio_adquisicion(double precio_adquisicion) {
        this.precio_adquisicion = precio_adquisicion;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Timestamp getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Timestamp getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Timestamp fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

     public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public double getMontoTotalVendido() {
        return montoTotalVendido;
    }

    public void setMontoTotalVendido(double montoTotalVendido) {
        this.montoTotalVendido = montoTotalVendido;
    }
    
}
