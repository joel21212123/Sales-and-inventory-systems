package bodega_myc_.Modelo;

public class ClaseDetalleFactura {
    
    private int id_detalle_factura;
    private int factura_id;
    private int producto_id;
    private int cantidad_detalle;
    private double precio_unitario;
    private double subtotal;

    public ClaseDetalleFactura() {
    }

    public int getId_detalle_factura() {
        return id_detalle_factura;
    }

    public void setId_detalle_factura(int id_detalle_factura) {
        this.id_detalle_factura = id_detalle_factura;
    }

    public int getFactura_id() {
        return factura_id;
    }

    public void setFactura_id(int factura_id) {
        this.factura_id = factura_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad_detalle() {
        return cantidad_detalle;
    }

    public void setCantidad_detalle(int cantidad_detalle) {
        this.cantidad_detalle = cantidad_detalle;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "id=" + id_detalle_factura +
                ", facturaId=" + factura_id +
                ", productoId=" + producto_id +
                ", cantidad=" + cantidad_detalle +
                ", precio=" + precio_unitario +
                ", subtotal=" + subtotal +
                '}';
    }
}