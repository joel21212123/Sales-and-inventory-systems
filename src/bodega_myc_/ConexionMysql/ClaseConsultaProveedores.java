
package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseProveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClaseConsultaProveedores {
    private final ConexionClass conexion = new ConexionClass();

    // Registrar proveedor
    public boolean registrar(ClaseProveedor proveedor) {
        String sql = "INSERT INTO proveedores (ruc_proveedores, nombre_proveedores, "
                   + "telefono_proveedores, email_proveedores) "
                   + "VALUES (?, ?, ?, ?)";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, proveedor.getRuc_proveedores());
            ps.setString(2, proveedor.getNombre_proveedores());
            ps.setString(3, proveedor.getTelefono_proveedores());
            ps.setString(4, proveedor.getEmail_proveedores());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }

    // Modificar proveedor
    public boolean modificar(ClaseProveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre_proveedores = ?, telefono_proveedores = ?, "
                   + "email_proveedores = ? WHERE ruc_proveedores = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, proveedor.getNombre_proveedores());
            ps.setString(2, proveedor.getTelefono_proveedores());
            ps.setString(3, proveedor.getEmail_proveedores());
            ps.setString(4, proveedor.getRuc_proveedores());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al modificar proveedor: " + e.getMessage());
            return false;
        }
    }

    // Eliminar proveedor
    public boolean eliminar(String ruc) {
        String sql = "DELETE FROM proveedores WHERE ruc_proveedores = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ruc);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    // Obtener proveedor por RUC
    public ClaseProveedor obtenerPorRUC(String ruc) {
        String sql = "SELECT * FROM proveedores WHERE ruc_proveedores = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, ruc);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseProveedor proveedor = new ClaseProveedor();
                    proveedor.setId_proveedores(rs.getInt("id_proveedores"));
                    proveedor.setRuc_proveedores(rs.getString("ruc_proveedores"));
                    proveedor.setNombre_proveedores(rs.getString("nombre_proveedores"));
                    proveedor.setTelefono_proveedores(rs.getString("telefono_proveedores"));
                    proveedor.setEmail_proveedores(rs.getString("email_proveedores"));
                    return proveedor;
                }
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener proveedor: " + e.getMessage());
        }
        return null;
    }

    // Obtener todos los proveedores
    public List<ClaseProveedor> obtenerTodos() {
        List<ClaseProveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClaseProveedor proveedor = new ClaseProveedor();
                proveedor.setId_proveedores(rs.getInt("id_proveedores"));
                proveedor.setRuc_proveedores(rs.getString("ruc_proveedores"));
                proveedor.setNombre_proveedores(rs.getString("nombre_proveedores"));
                proveedor.setTelefono_proveedores(rs.getString("telefono_proveedores"));
                proveedor.setEmail_proveedores(rs.getString("email_proveedores"));
                proveedores.add(proveedor);
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }
    // Verificar si existe un proveedor por nombre
    public boolean existeProveedor(String nombre) {
        String sql = "SELECT COUNT(*) FROM proveedores WHERE nombre_proveedores = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch(SQLException e) {
            System.err.println("Error al verificar proveedor: " + e.getMessage());
        }
        return false;
    }
}
