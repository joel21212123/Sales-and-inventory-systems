package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseFactura;
import bodega_myc_.Modelo.ClaseProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClaseConsultaFactura {
    private final ConexionClass conexion = new ConexionClass();

    // Registrar factura
    public boolean registrar(ClaseFactura factura) {
        String sql = "INSERT INTO facturas (numero_factura, fecha_factura, usuario_id, cliente_id, "
                + "subtotal, descuento, igv, total, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, factura.getNumero_factura());
            ps.setTimestamp(2, new java.sql.Timestamp(factura.getFecha_factura().getTime()));
            ps.setInt(3, factura.getUsuario_id());
            ps.setInt(4, factura.getCliente_id());
            ps.setDouble(5, factura.getSubtotal());
            ps.setDouble(6, factura.getDescuento());
            ps.setDouble(7, factura.getIgv());
            ps.setDouble(8, factura.getTotal());
            ps.setString(9, factura.getEstado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar factura: " + e.getMessage());
            return false;
        }
    }

    // Modificar factura
    public boolean modificar(ClaseFactura factura) {
        String sql = "UPDATE facturas SET numero_factura = ?, fecha_factura = ?, usuario_id = ?, "
                + "cliente_id = ?, subtotal = ?, descuento = ?, igv = ?, total = ?, estado = ? "
                + "WHERE id_facturas = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, factura.getNumero_factura());
            ps.setTimestamp(2, new java.sql.Timestamp(factura.getFecha_factura().getTime()));
            ps.setInt(3, factura.getUsuario_id());
            ps.setInt(4, factura.getCliente_id());
            ps.setDouble(5, factura.getSubtotal());
            ps.setDouble(6, factura.getDescuento());
            ps.setDouble(7, factura.getIgv());
            ps.setDouble(8, factura.getTotal());
            ps.setString(9, factura.getEstado());
            ps.setInt(10, factura.getId_facturas());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar factura: " + e.getMessage());
            return false;
        }
    }

    // Eliminar factura
    public boolean eliminar(int idFactura) {
        String sql = "DELETE FROM facturas WHERE id_facturas = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }

    // Obtener factura por ID
    public ClaseFactura obtenerPorId(int idFactura) {
        String sql = "SELECT * FROM facturas WHERE id_facturas = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseFactura factura = new ClaseFactura();
                    factura.setId_facturas(rs.getInt("id_facturas"));
                    factura.setNumero_factura(rs.getString("numero_factura"));
                    factura.setUsuario_id(rs.getInt("usuario_id"));
                    factura.setCliente_id(rs.getInt("cliente_id"));
                    factura.setSubtotal(rs.getDouble("subtotal"));
                    factura.setDescuento(rs.getDouble("descuento"));
                    factura.setIgv(rs.getDouble("igv"));
                    factura.setTotal(rs.getDouble("total"));
                    factura.setEstado(rs.getString("estado"));
                    factura.setFecha_factura(rs.getTimestamp("fecha_factura"));
                    return factura;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener factura: " + e.getMessage());
        }
        return null;
    }

    // Obtener todas las facturas
    public List<ClaseFactura> obtenerTodas() {
        List<ClaseFactura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM facturas";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClaseFactura factura = new ClaseFactura();
                factura.setId_facturas(rs.getInt("id_facturas"));
                factura.setNumero_factura(rs.getString("numero_factura"));
                factura.setUsuario_id(rs.getInt("usuario_id"));
                factura.setCliente_id(rs.getInt("cliente_id"));
                factura.setSubtotal(rs.getDouble("subtotal"));
                factura.setDescuento(rs.getDouble("descuento"));
                factura.setIgv(rs.getDouble("igv"));
                factura.setTotal(rs.getDouble("total"));
                factura.setEstado(rs.getString("estado"));
                factura.setFecha_factura(rs.getTimestamp("fecha_factura"));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener facturas: " + e.getMessage());
        }
        return facturas;
    }

    // Productos vendidos (usa la vista SQL vista_productos_vendidos)
    public List<ClaseProducto> obtenerProductosVendidos() {
        List<ClaseProducto> productos = new ArrayList<>();
        String sql = "SELECT * FROM vista_productos_vendidos";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClaseProducto p = new ClaseProducto();
                p.setIdproductos(rs.getInt("idproductos"));
                p.setNombre_Productos(rs.getString("nombre_Productos"));
                p.setCantidadVendida(rs.getInt("cantidad_vendida"));
                p.setMontoTotalVendido(rs.getDouble("monto_total_vendido"));
                productos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos vendidos: " + e.getMessage());
        }
        return productos;
    }

    // Ventas por usuario (usa la vista SQL vista_ventas_usuarios)
    public List<Map<String, Object>> obtenerVentasPorUsuario() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas_usuarios";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_usuario", rs.getInt("id_usuario"));
                fila.put("nombre_usuario", rs.getString("nombre_usuario"));
                fila.put("total_ventas", rs.getInt("total_ventas"));
                fila.put("monto_total_vendido", rs.getDouble("monto_total_vendido"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por usuario: " + e.getMessage());
        }
        return resultados;
    }

    // Ventas por categoría (usa la vista SQL vista_ventas_categorias)
    public List<Map<String, Object>> obtenerVentasPorCategoria() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT * FROM vista_ventas_categorias";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("categoria", rs.getString("categoria"));
                fila.put("total_vendido", rs.getInt("total_vendido"));
                fila.put("monto_total_vendido", rs.getDouble("monto_total_vendido"));
                resultados.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por categoría: " + e.getMessage());
        }
        return resultados;
    }
}