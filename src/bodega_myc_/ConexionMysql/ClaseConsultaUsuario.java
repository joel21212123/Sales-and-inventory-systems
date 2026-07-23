
package bodega_myc_.ConexionMysql;

import bodega_myc_.Modelo.ClaseUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClaseConsultaUsuario  {
    // Usamos instancia única de conexión
    private final ConexionClass conexion = new ConexionClass();
    
 // Registrar usuario
    public boolean registrar(ClaseUsuario usuario) {
        String sql = "INSERT INTO usuario (nombre_usuario, username, password) VALUES (?, ?, ?)";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getPassword());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // Autenticar usuario
    public boolean autenticar(String username, String password) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ? AND password = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch(SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        }
        return false;
    }

    // Modificar usuario
    public boolean modificar(ClaseUsuario usuario) {
        String sql = "UPDATE usuario SET nombre_usuario = ?, username = ?, password = ? "
                   + "WHERE id_usuario = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4, usuario.getId_usuario());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar usuario
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Obtener usuario por ID
    public ClaseUsuario obtenerPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClaseUsuario usuario = new ClaseUsuario();
                    usuario.setId_usuario(rs.getInt("id_usuario"));
                    usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                    usuario.setUsername(rs.getString("username"));
                    return usuario;
                }
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }

    // Obtener todos los usuarios
    public List<ClaseUsuario> obtenerTodos() {
        List<ClaseUsuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClaseUsuario usuario = new ClaseUsuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }
    
    public ClaseUsuario obtenerPorUsername(String username) {
    String sql = "SELECT * FROM usuario WHERE username = ?";
    try (Connection con = conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, username);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ClaseUsuario usuario = new ClaseUsuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setUsername(rs.getString("username"));
                return usuario;
            }
        }
    } catch(SQLException e) {
        System.err.println("Error al obtener usuario: " + e.getMessage());
    }
    return null;
}
}