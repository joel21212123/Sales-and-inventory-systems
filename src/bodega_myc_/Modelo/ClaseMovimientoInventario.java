
package bodega_myc_.Modelo;

import java.sql.Timestamp;
public class ClaseMovimientoInventario {
  private int id_movimientos_inventario;
    private String nombre_producto;
    private String tipo_movimientos;
    private int cantidad_movimientos;
    private String motivo_movimientos;
    private Timestamp fecha_movimientos;
    private int usuario_id;
    private String nombreProductoCache;
    
    public ClaseMovimientoInventario() {
    }

    public int getId_movimientos_inventario() {
        return id_movimientos_inventario;
    }

    public void setId_movimientos_inventario(int id_movimientos_inventario) {
        this.id_movimientos_inventario = id_movimientos_inventario;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getTipo_movimientos() {
        return tipo_movimientos;
    }

    public void setTipo_movimientos(String tipo_movimientos) {
        this.tipo_movimientos = tipo_movimientos;
    }

    public int getCantidad_movimientos() {
        return cantidad_movimientos;
    }

    public void setCantidad_movimientos(int cantidad_movimientos) {
        this.cantidad_movimientos = cantidad_movimientos;
    }

    public String getMotivo_movimientos() {
        return motivo_movimientos;
    }

    public void setMotivo_movimientos(String motivo_movimientos) {
        this.motivo_movimientos = motivo_movimientos;
    }

    public Timestamp getFecha_movimientos() {
        return fecha_movimientos;
    }

    public void setFecha_movimientos(Timestamp fecha_movimientos) {
        this.fecha_movimientos = fecha_movimientos;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getProducto_id() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setProducto_id(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   public String getNombreProductoCache() {
        return nombreProductoCache;
    }

    public void setNombreProductoCache(String nombreProductoCache) {
        this.nombreProductoCache = nombreProductoCache;
    }

    
    
}
