package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseMovimientoInventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClaseConsultaMovimientosInventario {
    private final ConexionClass conexion = new ConexionClass();

    // Registrar movimiento
    public boolean registrar(ClaseMovimientoInventario movimiento) {
        String sql = "INSERT INTO movimientos_inventario (producto_id, tipo_movimientos, "
                   + "cantidad_movimientos, motivo_movimientos, usuario_id) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, movimiento.getProducto_id());
            ps.setString(2, movimiento.getTipo_movimientos());
            ps.setInt(3, movimiento.getCantidad_movimientos());
            ps.setString(4, movimiento.getMotivo_movimientos());
            ps.setInt(5, movimiento.getUsuario_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar movimiento: " + e.getMessage());
            return false;
        }
    }

    // Obtener por ID (con JOIN para traer también el nombre del producto)
    public ClaseMovimientoInventario obtenerPorId(int idMovimiento) {
        String sql = "SELECT m.id_movimientos_inventario, m.producto_id, m.tipo_movimientos, "
                   + "m.cantidad_movimientos, m.motivo_movimientos, m.fecha_movimientos, m.usuario_id, "
                   + "p.nombre_Productos "
                   + "FROM movimientos_inventario m "
                   + "JOIN productos p ON m.producto_id = p.idproductos "
                   + "WHERE m.id_movimientos_inventario = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMovimiento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseMovimientoInventario movimiento = new ClaseMovimientoInventario();
                    movimiento.setId_movimientos_inventario(rs.getInt("id_movimientos_inventario"));
                    movimiento.setProducto_id(rs.getInt("producto_id"));
                    movimiento.setTipo_movimientos(rs.getString("tipo_movimientos"));
                    movimiento.setCantidad_movimientos(rs.getInt("cantidad_movimientos"));
                    movimiento.setMotivo_movimientos(rs.getString("motivo_movimientos"));
                    movimiento.setFecha_movimientos(rs.getTimestamp("fecha_movimientos"));
                    movimiento.setUsuario_id(rs.getInt("usuario_id"));
                    movimiento.setNombreProductoCache(rs.getString("nombre_Productos"));
                    return movimiento;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener movimiento: " + e.getMessage());
        }
        return null;
    }

    // Obtener por producto (ahora recibe el ID del producto, no el nombre)
    public List<ClaseMovimientoInventario> obtenerPorProducto(int productoId) {
        List<ClaseMovimientoInventario> movimientos = new ArrayList<>();
        String sql = "SELECT m.id_movimientos_inventario, m.producto_id, m.tipo_movimientos, "
                   + "m.cantidad_movimientos, m.motivo_movimientos, m.fecha_movimientos, m.usuario_id, "
                   + "p.nombre_Productos "
                   + "FROM movimientos_inventario m "
                   + "JOIN productos p ON m.producto_id = p.idproductos "
                   + "WHERE m.producto_id = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClaseMovimientoInventario movimiento = new ClaseMovimientoInventario();
                    movimiento.setId_movimientos_inventario(rs.getInt("id_movimientos_inventario"));
                    movimiento.setProducto_id(rs.getInt("producto_id"));
                    movimiento.setTipo_movimientos(rs.getString("tipo_movimientos"));
                    movimiento.setCantidad_movimientos(rs.getInt("cantidad_movimientos"));
                    movimiento.setMotivo_movimientos(rs.getString("motivo_movimientos"));
                    movimiento.setFecha_movimientos(rs.getTimestamp("fecha_movimientos"));
                    movimiento.setUsuario_id(rs.getInt("usuario_id"));
                    movimiento.setNombreProductoCache(rs.getString("nombre_Productos"));
                    movimientos.add(movimiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos por producto: " + e.getMessage());
        }
        return movimientos;
    }

    // Obtener todos los movimientos (optimizado con JOIN, evita N+1 queries)
    public List<ClaseMovimientoInventario> obtenerTodos() {
        List<ClaseMovimientoInventario> movimientos = new ArrayList<>();
        String sql = "SELECT m.id_movimientos_inventario, m.producto_id, m.tipo_movimientos, "
                   + "m.cantidad_movimientos, m.motivo_movimientos, m.fecha_movimientos, m.usuario_id, "
                   + "p.nombre_Productos "
                   + "FROM movimientos_inventario m "
                   + "JOIN productos p ON m.producto_id = p.idproductos "
                   + "ORDER BY m.fecha_movimientos DESC";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClaseMovimientoInventario movimiento = new ClaseMovimientoInventario();
                movimiento.setId_movimientos_inventario(rs.getInt("id_movimientos_inventario"));
                movimiento.setProducto_id(rs.getInt("producto_id"));
                movimiento.setTipo_movimientos(rs.getString("tipo_movimientos"));
                movimiento.setCantidad_movimientos(rs.getInt("cantidad_movimientos"));
                movimiento.setMotivo_movimientos(rs.getString("motivo_movimientos"));
                movimiento.setFecha_movimientos(rs.getTimestamp("fecha_movimientos"));
                movimiento.setUsuario_id(rs.getInt("usuario_id"));
                movimiento.setNombreProductoCache(rs.getString("nombre_Productos"));
                movimientos.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos: " + e.getMessage());
        }
        return movimientos;
    }

    // Actualizar movimiento
    public boolean actualizar(ClaseMovimientoInventario movimiento) {
        String sql = "UPDATE movimientos_inventario SET producto_id = ?, tipo_movimientos = ?, "
                   + "cantidad_movimientos = ?, motivo_movimientos = ? "
                   + "WHERE id_movimientos_inventario = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, movimiento.getProducto_id());
            ps.setString(2, movimiento.getTipo_movimientos());
            ps.setInt(3, movimiento.getCantidad_movimientos());
            ps.setString(4, movimiento.getMotivo_movimientos());
            ps.setInt(5, movimiento.getId_movimientos_inventario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar movimiento: " + e.getMessage());
            return false;
        }
    }

    // Eliminar movimiento
    public boolean eliminar(int id) {
        String sql = "DELETE FROM movimientos_inventario WHERE id_movimientos_inventario = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar movimiento: " + e.getMessage());
            return false;
        }
    }
}