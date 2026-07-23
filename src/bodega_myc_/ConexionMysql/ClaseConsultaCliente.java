package bodega_myc_.ConexionMysql;


import bodega_myc_.Modelo.ClaseCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClaseConsultaCliente {
    private final ConexionClass conexion = new ConexionClass();

    // Registrar cliente
    public boolean registrar(ClaseCliente cliente) {
        String sql = "INSERT INTO clientes (nombre_cliente, dni, telefono) VALUES (?, ?, ?)";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNombre_cliente());
            ps.setString(2, cliente.getDni());
            ps.setString(3, cliente.getTelefono());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    // Modificar cliente
    public boolean modificar(ClaseCliente cliente) {
        String sql = "UPDATE clientes SET nombre_cliente = ?, dni = ?, telefono = ? "
                   + "WHERE id_clientes = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNombre_cliente());
            ps.setString(2, cliente.getDni());
            ps.setString(3, cliente.getTelefono());
            ps.setInt(4, cliente.getId_clientes());
            
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al modificar cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cliente
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_clientes = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los clientes
    public List<ClaseCliente> obtenerTodos() {
        List<ClaseCliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ClaseCliente cliente = new ClaseCliente();
                cliente.setId_clientes(rs.getInt("id_clientes"));
                cliente.setNombre_cliente(rs.getString("nombre_cliente"));
                cliente.setDni(rs.getString("dni"));
                cliente.setTelefono(rs.getString("telefono"));
                clientes.add(cliente);
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return clientes;
    }
    
     // Método para loguear excepciones SQL con más detalle
    private void logSQLException(String operacion, SQLException e) {
        System.err.println("Error al " + operacion + ":");
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("Message: " + e.getMessage());
        e.printStackTrace();
    }
     // Nuevo método para obtener el reporte de clientes
    public List<ClaseCliente> obtenerReporteClientes() {
    List<ClaseCliente> clientes = new ArrayList<>();
    String sql = "SELECT * FROM vista_reporte_clientes";
    
    try (Connection con = conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            ClaseCliente cliente = new ClaseCliente();
            cliente.setId_clientes(rs.getInt("id_clientes"));
            cliente.setDni(rs.getString("dni"));
            cliente.setNombre_cliente(rs.getString("nombre_cliente"));
            cliente.setFecha_registro(rs.getTimestamp("fecha"));
            cliente.setCantidadCompras(rs.getInt("cantidad_compras"));
            cliente.setMontoTotalGastado(rs.getDouble("monto_total_gastado"));
            clientes.add(cliente);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener reporte de clientes: " + e.getMessage());
    }
    return clientes;
}

    // Método para búsqueda filtrada
    public List<ClaseCliente> buscarClientesFiltrados(String dni, String nombre, Date fecha) {
        List<ClaseCliente> clientes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM vista_reporte_clientes WHERE 1=1");
        
        if (dni != null && !dni.isEmpty()) sql.append(" AND dni = ?");
        if (nombre != null && !nombre.isEmpty()) sql.append(" AND nombre_cliente LIKE ?");
        if (fecha != null) sql.append(" AND DATE(fecha) = ?");
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (dni != null && !dni.isEmpty()) ps.setString(paramIndex++, dni);
            if (nombre != null && !nombre.isEmpty()) ps.setString(paramIndex++, "%" + nombre + "%");
            if (fecha != null) ps.setDate(paramIndex++, new java.sql.Date(fecha.getTime()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Mismo mapeo que en obtenerReporteClientes
            }
        } catch (SQLException e) {
            System.err.println("Error en búsqueda de clientes: " + e.getMessage());
        }
        return clientes;
    }
     // Verificar si existe un cliente por nombre
    public boolean existeCliente(String nombre) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE nombre_cliente = ?";
        
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombre);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch(SQLException e) {
            System.err.println("Error al verificar cliente: " + e.getMessage());
        }
        return false;
    }
}