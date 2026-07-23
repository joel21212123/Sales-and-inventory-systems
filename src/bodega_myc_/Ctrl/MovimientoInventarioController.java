package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaMovimientosInventario;
import bodega_myc_.Modelo.ClaseMovimientoInventario;
import bodega_myc_.Vista.MOVIMIENTO_INVENTARIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MovimientoInventarioController implements ActionListener {
    private ClaseMovimientoInventario mod;
    private ClaseConsultaMovimientosInventario modConsulta;
    private MOVIMIENTO_INVENTARIO frm;
    private int usuarioId;
    
    public MovimientoInventarioController(ClaseMovimientoInventario mod, 
            ClaseConsultaMovimientosInventario modConsulta, 
            MOVIMIENTO_INVENTARIO frm, int usuarioId) {
        
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;
        this.usuarioId = usuarioId;
        
        this.frm.btnNuevoMovimientoInventario.addActionListener(this);
        this.frm.btnLeerMovimientoInventario.addActionListener(this);
        this.frm.btnModificarMovimientoInventario.addActionListener(this);
        this.frm.btnEliminarMovimientoInventario.addActionListener(this);
        
        // Configurar campos automáticos
        frm.txtUsuarioId.setText(String.valueOf(usuarioId));
        frm.txtUsuarioId.setEditable(false);
        frm.txtFechaMovimiento.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        frm.txtFechaMovimiento.setEditable(false);
        
        // Cargar datos iniciales
        cargarMovimientos();
    }
    
    public void iniciar() {
        frm.setTitle("Movimientos de Inventario");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnNuevoMovimientoInventario) {
            guardarMovimiento();
        } 
        else if (e.getSource() == frm.btnLeerMovimientoInventario) {
            seleccionarMovimientoDeTabla();
        }
        else if (e.getSource() == frm.btnModificarMovimientoInventario) {
            actualizarMovimiento();
        }
        else if (e.getSource() == frm.btnEliminarMovimientoInventario) {
            eliminarMovimiento();
        }
    }
    
    private void guardarMovimiento() {
        try {
            // Validar campos obligatorios
            if (frm.txtNombreProducto.getText().isEmpty() || 
                frm.cbxTipoMovimiento.getSelectedIndex() == 0) {
                
                JOptionPane.showMessageDialog(frm, "Nombre de producto y tipo de movimiento son obligatorios");
                return;
            }
            
            // Configurar modelo con datos de la vista
            mod.setNombre_producto(frm.txtNombreProducto.getText());
            mod.setTipo_movimientos(frm.cbxTipoMovimiento.getSelectedItem().toString());
            mod.setCantidad_movimientos(Integer.parseInt(frm.txtCantidadMovimientos.getText()));
            mod.setMotivo_movimientos(frm.txtMotivoMovimientos.getText());
            mod.setFecha_movimientos(new Timestamp(new Date().getTime()));
            mod.setUsuario_id(usuarioId);
            
            // Registrar movimiento
            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(frm, "Movimiento registrado exitosamente");
                limpiarCampos();
                cargarMovimientos();
            } else {
                JOptionPane.showMessageDialog(frm, "Error al registrar movimiento");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frm, "La cantidad debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void cargarMovimientos() {
        try {
            List<ClaseMovimientoInventario> movimientos = modConsulta.obtenerTodos();
            DefaultTableModel modelo = (DefaultTableModel) frm.tblMovimientoInventario.getModel();
            modelo.setRowCount(0); // Limpiar tabla
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            for (ClaseMovimientoInventario movimiento : movimientos) {
                modelo.addRow(new Object[]{
                    movimiento.getId_movimientos_inventario(),
                    movimiento.getNombre_producto(),
                    movimiento.getTipo_movimientos(),
                    movimiento.getCantidad_movimientos(),
                    movimiento.getMotivo_movimientos(),
                    sdf.format(movimiento.getFecha_movimientos()),
                    movimiento.getUsuario_id()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error al cargar movimientos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void seleccionarMovimientoDeTabla() {
        int filaSeleccionada = frm.tblMovimientoInventario.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) frm.tblMovimientoInventario.getModel();
            
            frm.txtIdMovimientos.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            frm.txtNombreProducto.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            frm.cbxTipoMovimiento.setSelectedItem(modelo.getValueAt(filaSeleccionada, 2).toString());
            frm.txtCantidadMovimientos.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
            frm.txtMotivoMovimientos.setText(modelo.getValueAt(filaSeleccionada, 4).toString());
            frm.txtFechaMovimiento.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
            frm.txtUsuarioId.setText(modelo.getValueAt(filaSeleccionada, 6).toString());
        } else {
            JOptionPane.showMessageDialog(frm, "Seleccione un movimiento de la tabla");
        }
    }
    
    private void actualizarMovimiento() {
        try {
            // Validar selección
            if (frm.txtIdMovimientos.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frm, "Seleccione un movimiento para actualizar");
                return;
            }
            
            // Validar campos obligatorios
            if (frm.txtNombreProducto.getText().isEmpty() || 
                frm.cbxTipoMovimiento.getSelectedIndex() == 0) {
                
                JOptionPane.showMessageDialog(frm, "Nombre de producto y tipo de movimiento son obligatorios");
                return;
            }
            
            // Configurar modelo con datos de la vista
            mod.setId_movimientos_inventario(Integer.parseInt(frm.txtIdMovimientos.getText()));
            mod.setNombre_producto(frm.txtNombreProducto.getText());
            mod.setTipo_movimientos(frm.cbxTipoMovimiento.getSelectedItem().toString());
            mod.setCantidad_movimientos(Integer.parseInt(frm.txtCantidadMovimientos.getText()));
            mod.setMotivo_movimientos(frm.txtMotivoMovimientos.getText());
            mod.setUsuario_id(usuarioId);
            
            // Actualizar movimiento
            if (modConsulta.actualizar(mod)) {
                JOptionPane.showMessageDialog(frm, "Movimiento actualizado exitosamente");
                limpiarCampos();
                cargarMovimientos();
            } else {
                JOptionPane.showMessageDialog(frm, "Error al actualizar movimiento");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frm, "ID o cantidad inválida", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void eliminarMovimiento() {
        // Validar selección
        if (frm.txtIdMovimientos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frm, "Seleccione un movimiento para eliminar");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            frm, 
            "¿Está seguro de eliminar este movimiento?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(frm.txtIdMovimientos.getText());
                if (modConsulta.eliminar(id)) {
                    JOptionPane.showMessageDialog(frm, "Movimiento eliminado exitosamente");
                    limpiarCampos();
                    cargarMovimientos();
                } else {
                    JOptionPane.showMessageDialog(frm, "Error al eliminar movimiento");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frm, "ID de movimiento inválido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void limpiarCampos() {
        frm.txtIdMovimientos.setText("");
        frm.txtNombreProducto.setText("");
        frm.cbxTipoMovimiento.setSelectedIndex(0);
        frm.txtCantidadMovimientos.setText("");
        frm.txtMotivoMovimientos.setText("");
        frm.txtFechaMovimiento.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }
    
    // Método para actualizar la tabla después de operaciones CRUD
    public void actualizarTabla() {
        cargarMovimientos();
    }
}