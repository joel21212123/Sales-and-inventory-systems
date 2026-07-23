
package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseVentaProducto;
import bodega_myc_.Modelo.ClaseProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ClaseConsultaVentas {
    private final ConexionClass conexion = new ConexionClass();
    private final ClaseConsultaCliente consultaClientes = new ClaseConsultaCliente();
    private final ClaseConsultaProducto consultaProducto = new ClaseConsultaProducto();

    // Registrar venta con validaciones
    public String registrarVenta(ClaseVentaProducto venta) {
        // Validar si el cliente existe
        if (!consultaClientes.existeCliente(venta.getNombre_cliente())) {
            return "CLIENTE_NO_EXISTE"; // Código de error
        }
        
        // Validar stock y stock mínimo
        ClaseProducto producto = consultaProducto.obtenerPorNombre(venta.getNombre_Productos());
        if (producto == null) {
            return "PRODUCTO_NO_EXISTE";
        }
        
        int nuevoStock = producto.getStock() - venta.getCantidad();
        if (nuevoStock < 0) {
            return "STOCK_INSUFICIENTE";
        }
        
        // Validar stock mínimo (excepto para administrador ID 1)
        if (nuevoStock < producto.getStock_minimo() && venta.getUsuario_id() != 1) {
            return "STOCK_MINIMO_ALERTA";
        }
        
        // Registrar la venta
        if (registrar(venta)) {
            return "EXITO";
        }
        return "ERROR_REGISTRO";
    }

    // Registrar venta (método interno)
    private boolean registrar(ClaseVentaProducto venta) {
        String sql = "INSERT INTO venta_productos (numero_factura, usuario_id, nombre_cliente, "
                   + "nombre_Productos, precio_venta, cantidad, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, venta.getNumero_factura());
            ps.setInt(2, venta.getUsuario_id());
            ps.setString(3, venta.getNombre_cliente());
            ps.setString(4, venta.getNombre_Productos());
            ps.setDouble(5, venta.getPrecio_venta());
            ps.setInt(6, venta.getCantidad());
            ps.setString(7, venta.getEstado());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al registrar venta: " + e.getMessage());
            return false;
        }
    }

    // Obtener venta por ID
    public ClaseVentaProducto obtenerPorId(int idVenta) {
        String sql = "SELECT * FROM venta_productos WHERE id_venta = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idVenta);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseVentaProducto venta = new ClaseVentaProducto();
                    venta.setId_venta(rs.getInt("id_venta"));
                    venta.setNumero_factura(rs.getString("numero_factura"));
                    venta.setFecha_venta(rs.getTimestamp("fecha_venta"));
                    venta.setUsuario_id(rs.getInt("usuario_id"));
                    venta.setNombre_cliente(rs.getString("nombre_cliente"));
                    venta.setNombre_Productos(rs.getString("nombre_Productos"));
                    venta.setPrecio_venta(rs.getDouble("precio_venta"));
                    venta.setCantidad(rs.getInt("cantidad"));
                    venta.setEstado(rs.getString("estado"));
                    return venta;
                }
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener venta: " + e.getMessage());
        }
        return null;
    }

    // Obtener todas las ventas
    public List<ClaseVentaProducto> obtenerTodas() {
        List<ClaseVentaProducto> ventas = new ArrayList<>();
        String sql = "SELECT * FROM venta_productos";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClaseVentaProducto venta = new ClaseVentaProducto();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setNumero_factura(rs.getString("numero_factura"));
                venta.setFecha_venta(rs.getTimestamp("fecha_venta"));
                venta.setUsuario_id(rs.getInt("usuario_id"));
                venta.setNombre_cliente(rs.getString("nombre_cliente"));
                venta.setNombre_Productos(rs.getString("nombre_Productos"));
                venta.setPrecio_venta(rs.getDouble("precio_venta"));
                venta.setCantidad(rs.getInt("cantidad"));
                venta.setEstado(rs.getString("estado"));
                ventas.add(venta);
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener ventas: " + e.getMessage());
        }
        return ventas;
    }

    // Método para productos vendidos
    public List<ClaseProducto> obtenerProductosVendidos() {
        List<ClaseProducto> productos = new ArrayList<>();
        String sql = "SELECT nombre_Productos, SUM(cantidad) AS cantidad_vendida, "
                   + "SUM(precio_venta * cantidad) AS monto_total_vendido "
                   + "FROM venta_productos "
                   + "GROUP BY nombre_Productos";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClaseProducto p = new ClaseProducto();
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

    // Método para ventas por usuario
    public List<Map<String, Object>> obtenerVentasPorUsuario() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, "
                   + "COUNT(v.id_venta) AS total_ventas, "
                   + "SUM(v.precio_venta * v.cantidad) AS monto_total_vendido "
                   + "FROM usuario u "
                   + "JOIN venta_productos v ON u.id_usuario = v.usuario_id "
                   + "GROUP BY u.id_usuario";
        
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

    // Método para ventas por categoría
    public List<Map<String, Object>> obtenerVentasPorCategoria() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        String sql = "SELECT p.categoria, "
                   + "SUM(v.cantidad) AS total_vendido, "
                   + "SUM(v.precio_venta * v.cantidad) AS monto_total_vendido "
                   + "FROM venta_productos v "
                   + "JOIN productos p ON v.nombre_Productos = p.nombre_Productos "
                   + "GROUP BY p.categoria";
        
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
