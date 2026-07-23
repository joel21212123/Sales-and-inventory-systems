package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClaseConsultaProducto {
    private final ConexionClass conexion = new ConexionClass();

    private static final String COLUMNAS =
            "idproductos, nombre_Productos, descripcion, precio_adquisicion, "
          + "precio_venta, stock, stock_minimo, categoria, fecha_creacion, fecha_actualizacion";

    // Registrar producto
    public boolean registrar(ClaseProducto producto) {
        String sql = "INSERT INTO productos (nombre_Productos, descripcion, precio_adquisicion, "
                   + "precio_venta, stock, stock_minimo, categoria) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre_Productos());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio_adquisicion());
            ps.setDouble(4, producto.getPrecio_venta());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getStock_minimo());
            ps.setString(7, producto.getCategoria());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los productos
    public List<ClaseProducto> obtenerTodos() {
        List<ClaseProducto> productos = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM productos";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    // Obtener producto por ID
    public ClaseProducto obtenerPorId(int id) {
        String sql = "SELECT " + COLUMNAS + " FROM productos WHERE idproductos = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
        }
        return null;
    }

    // Buscar productos por nombre (coincidencia parcial)
    public List<ClaseProducto> buscarPorNombre(String nombre) {
        List<ClaseProducto> productos = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM productos WHERE nombre_Productos LIKE ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar productos: " + e.getMessage());
        }
        return productos;
    }

    // Obtener producto por nombre exacto
    public ClaseProducto obtenerPorNombre(String nombre) {
        String sql = "SELECT " + COLUMNAS + " FROM productos WHERE nombre_Productos = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por nombre: " + e.getMessage());
        }
        return null;
    }

    // Actualizar producto
    public boolean actualizar(ClaseProducto producto) {
        String sql = "UPDATE productos SET nombre_Productos = ?, descripcion = ?, precio_adquisicion = ?, "
                   + "precio_venta = ?, stock = ?, stock_minimo = ?, categoria = ? "
                   + "WHERE idproductos = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre_Productos());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio_adquisicion());
            ps.setDouble(4, producto.getPrecio_venta());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getStock_minimo());
            ps.setString(7, producto.getCategoria());
            ps.setInt(8, producto.getIdproductos());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // Eliminar producto
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE idproductos = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    // Obtener productos con stock bajo
    public List<ClaseProducto> obtenerProductosStockBajo() {
        List<ClaseProducto> productos = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM productos WHERE stock < stock_minimo";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos con stock bajo: " + e.getMessage());
        }
        return productos;
    }

    // Método auxiliar para mapear ResultSet a objeto Producto
    private ClaseProducto mapearProducto(ResultSet rs) throws SQLException {
        ClaseProducto p = new ClaseProducto();
        p.setIdproductos(rs.getInt("idproductos"));
        p.setNombre_Productos(rs.getString("nombre_Productos"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio_adquisicion(rs.getDouble("precio_adquisicion"));
        p.setPrecio_venta(rs.getDouble("precio_venta"));
        p.setStock(rs.getInt("stock"));
        p.setStock_minimo(rs.getInt("stock_minimo"));
        p.setCategoria(rs.getString("categoria"));
        p.setFecha_creacion(rs.getTimestamp("fecha_creacion"));
        p.setFecha_actualizacion(rs.getTimestamp("fecha_actualizacion"));
        return p;
    }
}