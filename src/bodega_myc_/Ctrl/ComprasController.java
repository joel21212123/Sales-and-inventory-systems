package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaCompras;
import bodega_myc_.Modelo.ClaseCompra;
import bodega_myc_.Vista.COMPRAS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ComprasController implements ActionListener {
    private ClaseCompra mod;
    private ClaseConsultaCompras modConsulta;
    private COMPRAS frm;
    private int usuarioId;

    public ComprasController(ClaseCompra mod, ClaseConsultaCompras modConsulta, COMPRAS frm) {
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;

        this.frm.btnNuevoComras.addActionListener(this);
        this.frm.btnLeerCompras.addActionListener(this);
        this.frm.btnModificarCompras.addActionListener(this);
        this.frm.btnEliminarCompras.addActionListener(this);

        cargarCompras();
        frm.txtFechaCompras.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        frm.txtFechaCompras.setEditable(false);
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        frm.txtUsuarioId.setText(String.valueOf(usuarioId));
        frm.txtUsuarioId.setEditable(false);
    }

    public void iniciar() {
        frm.setTitle("Gestión de Compras");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void limpiarCampos() {
        frm.txtIdCompras.setText("");
        frm.txtProvedorID.setText("");
        frm.txtTotal.setText("");
    }

    private void cargarCompras() {
        try {
            List<ClaseCompra> compras = modConsulta.obtenerTodas();
            DefaultTableModel modelo = (DefaultTableModel) frm.tblCompras.getModel();
            modelo.setRowCount(0);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (ClaseCompra compra : compras) {
                modelo.addRow(new Object[]{
                    compra.getId_compras(),
                    compra.getProveedor_id(),
                    sdf.format(compra.getFecha_compras()),
                    compra.getTotal_compras(),
                    compra.getUsuario_id()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar compras: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarCompraDeTabla() {
        int filaSeleccionada = frm.tblCompras.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) frm.tblCompras.getModel();

            frm.txtIdCompras.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            frm.txtProvedorID.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            frm.txtTotal.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una compra de la tabla");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnNuevoComras) {
            guardarNuevaCompra();
        } else if (e.getSource() == frm.btnLeerCompras) {
            seleccionarCompraDeTabla();
        } else if (e.getSource() == frm.btnModificarCompras) {
            actualizarCompra();
        } else if (e.getSource() == frm.btnEliminarCompras) {
            eliminarCompra();
        }
    }

    private void guardarNuevaCompra() {
        if (frm.txtProvedorID.getText().isEmpty() || frm.txtTotal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Proveedor y Total son obligatorios");
            return;
        }

        try {
            mod.setProveedor_id(Integer.parseInt(frm.txtProvedorID.getText()));
            mod.setTotal_compras(Double.parseDouble(frm.txtTotal.getText()));
            mod.setUsuario_id(usuarioId);
            mod.setFecha_compras(new Timestamp(new Date().getTime()));

            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Compra registrada correctamente");
                limpiarCampos();
                cargarCompras();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void actualizarCompra() {
        if (frm.txtIdCompras.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione una compra para actualizar");
            return;
        }

        try {
            mod.setId_compras(Integer.parseInt(frm.txtIdCompras.getText()));
            mod.setProveedor_id(Integer.parseInt(frm.txtProvedorID.getText()));
            mod.setTotal_compras(Double.parseDouble(frm.txtTotal.getText()));
            mod.setUsuario_id(usuarioId);

            if (modConsulta.modificar(mod)) {
                JOptionPane.showMessageDialog(null, "Compra actualizada correctamente");
                cargarCompras();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void eliminarCompra() {
        if (frm.txtIdCompras.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione una compra para eliminar");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
            "¿Está seguro de eliminar esta compra?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(frm.txtIdCompras.getText());
                if (modConsulta.eliminar(id)) {
                    JOptionPane.showMessageDialog(null, "Compra eliminada correctamente");
                    limpiarCampos();
                    cargarCompras();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }
}