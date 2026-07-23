package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseDetalleFactura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClaseConsultaDetalleFactura {
    private final ConexionClass conexion = new ConexionClass();

    // Registrar detalle de factura
    public boolean registrar(ClaseDetalleFactura detalle) {
        String sql = "INSERT INTO detalle_factura (factura_id, producto_id, cantidad_detalle, "
                   + "precio_unitario, subtotal) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getFactura_id());
            ps.setInt(2, detalle.getProducto_id());
            ps.setInt(3, detalle.getCantidad_detalle());
            ps.setDouble(4, detalle.getPrecio_unitario());
            ps.setDouble(5, detalle.getSubtotal());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar detalle de factura: " + e.getMessage());
            return false;
        }
    }

    // Modificar detalle de factura
    public boolean modificar(ClaseDetalleFactura detalle) {
        String sql = "UPDATE detalle_factura SET factura_id = ?, producto_id = ?, "
                   + "cantidad_detalle = ?, precio_unitario = ?, subtotal = ? "
                   + "WHERE id_detalle_factura = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getFactura_id());
            ps.setInt(2, detalle.getProducto_id());
            ps.setInt(3, detalle.getCantidad_detalle());
            ps.setDouble(4, detalle.getPrecio_unitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setInt(6, detalle.getId_detalle_factura());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar detalle de factura: " + e.getMessage());
            return false;
        }
    }

    // Eliminar detalle de factura
    public boolean eliminar(int idDetalle) {
        String sql = "DELETE FROM detalle_factura WHERE id_detalle_factura = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle de factura: " + e.getMessage());
            return false;
        }
    }

    // Obtener detalle por ID
    public ClaseDetalleFactura obtenerPorId(int idDetalle) {
        String sql = "SELECT * FROM detalle_factura WHERE id_detalle_factura = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseDetalleFactura detalle = new ClaseDetalleFactura();
                    detalle.setId_detalle_factura(rs.getInt("id_detalle_factura"));
                    detalle.setFactura_id(rs.getInt("factura_id"));
                    detalle.setProducto_id(rs.getInt("producto_id"));
                    detalle.setCantidad_detalle(rs.getInt("cantidad_detalle"));
                    detalle.setPrecio_unitario(rs.getDouble("precio_unitario"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    return detalle;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalle de factura: " + e.getMessage());
        }
        return null;
    }

    // Obtener detalles por factura
    public List<ClaseDetalleFactura> obtenerPorFacturaId(int idFactura) {
        List<ClaseDetalleFactura> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_factura WHERE factura_id = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClaseDetalleFactura detalle = new ClaseDetalleFactura();
                    detalle.setId_detalle_factura(rs.getInt("id_detalle_factura"));
                    detalle.setFactura_id(rs.getInt("factura_id"));
                    detalle.setProducto_id(rs.getInt("producto_id"));
                    detalle.setCantidad_detalle(rs.getInt("cantidad_detalle"));
                    detalle.setPrecio_unitario(rs.getDouble("precio_unitario"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de factura: " + e.getMessage());
        }
        return detalles;
    }
}