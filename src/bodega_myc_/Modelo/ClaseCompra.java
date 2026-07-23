package bodega_myc_.Modelo;

import java.sql.Timestamp;

public class ClaseCompra {
    private int id_compras;
    private int proveedor_id;
    private Timestamp fecha_compras;
    private double total_compras;
    private int usuario_id;

    public ClaseCompra() {
    }

    public int getId_compras() {
        return id_compras;
    }

    public void setId_compras(int id_compras) {
        this.id_compras = id_compras;
    }

    public int getProveedor_id() {
        return proveedor_id;
    }

    public void setProveedor_id(int proveedor_id) {
        this.proveedor_id = proveedor_id;
    }

    public Timestamp getFecha_compras() {
        return fecha_compras;
    }

    public void setFecha_compras(Timestamp fecha_compras) {
        this.fecha_compras = fecha_compras;
    }

    public double getTotal_compras() {
        return total_compras;
    }

    public void setTotal_compras(double total_compras) {
        this.total_compras = total_compras;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}