package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseCompra;
import bodega_myc_.Modelo.ClaseDetalleCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClaseConsultaCompras {
    private final ConexionClass conexion = new ConexionClass();

    // Registrar compra
    public boolean registrar(ClaseCompra compra) {
        String sql = "INSERT INTO compras (proveedor_id, total_compras, usuario_id) "
                   + "VALUES (?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, compra.getProveedor_id());
            ps.setDouble(2, compra.getTotal_compras());
            ps.setInt(3, compra.getUsuario_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar compra: " + e.getMessage());
            return false;
        }
    }

    // Obtener compra por ID (CORREGIDO: ahora devuelve ClaseCompra, no un cast inválido)
    public ClaseCompra obtenerPorId(int idCompra) {
        String sql = "SELECT * FROM compras WHERE id_compras = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCompra);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseCompra compra = new ClaseCompra();
                    compra.setId_compras(rs.getInt("id_compras"));
                    compra.setProveedor_id(rs.getInt("proveedor_id"));
                    compra.setFecha_compras(rs.getTimestamp("fecha_compras"));
                    compra.setTotal_compras(rs.getDouble("total_compras"));
                    compra.setUsuario_id(rs.getInt("usuario_id"));
                    return compra;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener compra: " + e.getMessage());
        }
        return null;
    }

    // Obtener todas las compras
    public List<ClaseCompra> obtenerTodas() {
        List<ClaseCompra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClaseCompra compra = new ClaseCompra();
                compra.setId_compras(rs.getInt("id_compras"));
                compra.setProveedor_id(rs.getInt("proveedor_id"));
                compra.setFecha_compras(rs.getTimestamp("fecha_compras"));
                compra.setTotal_compras(rs.getDouble("total_compras"));
                compra.setUsuario_id(rs.getInt("usuario_id"));
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener compras: " + e.getMessage());
        }
        return compras;
    }

    // Modificar compra
    public boolean modificar(ClaseCompra compra) {
        String sql = "UPDATE compras SET proveedor_id = ?, total_compras = ?, usuario_id = ? WHERE id_compras = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, compra.getProveedor_id());
            ps.setDouble(2, compra.getTotal_compras());
            ps.setInt(3, compra.getUsuario_id());
            ps.setInt(4, compra.getId_compras());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar compra: " + e.getMessage());
            return false;
        }
    }

    // Eliminar compra
    public boolean eliminar(int idCompra) {
        String sql = "DELETE FROM compras WHERE id_compras = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar compra: " + e.getMessage());
            return false;
        }
    }

    // Reporte de compras (usa vista_detalle_compras)
    public List<Map<String, Object>> obtenerReporteCompras() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT * FROM vista_detalle_compras";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("proveedor", rs.getString("proveedor"));
                fila.put("monto_comprado", rs.getDouble("monto_comprado"));
                fila.put("producto", rs.getString("producto"));
                fila.put("fecha", rs.getDate("fecha"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reporte de compras: " + e.getMessage());
        }
        return resultados;
    }

    // Productos más/menos comprados
    public List<Object[]> obtenerProductosMasComprados() {
        List<Object[]> resultados = new ArrayList<>();
        String sql = "SELECT producto, COUNT(*) AS veces_comprado, SUM(monto_comprado) AS total_comprado "
                   + "FROM vista_detalle_compras "
                   + "GROUP BY producto "
                   + "ORDER BY veces_comprado DESC";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getString("producto"),
                    rs.getInt("veces_comprado"),
                    rs.getDouble("total_comprado")
                };
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos más comprados: " + e.getMessage());
        }
        return resultados;
    }
}