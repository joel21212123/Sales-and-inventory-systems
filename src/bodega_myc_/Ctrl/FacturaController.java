package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaFactura;
import bodega_myc_.Modelo.ClaseFactura;
import bodega_myc_.Vista.FACTURA;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FacturaController implements ActionListener {
    private ClaseFactura mod;
    private ClaseConsultaFactura modConsulta;
    private FACTURA frm;
    private int usuarioId;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public FacturaController(ClaseFactura mod, ClaseConsultaFactura modConsulta, FACTURA frm) {
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;

        this.frm.btnNuevoFactura.addActionListener(this);
        this.frm.btnLeerFactura.addActionListener(this);
        this.frm.btnModificarFactura.addActionListener(this);
        this.frm.btnEliminarFactura.addActionListener(this);

        cargarFacturas();
        frm.txtFechaFactura.setText(sdf.format(new Date()));
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
        frm.txtUsuarioID.setText(String.valueOf(usuarioId));
        frm.txtUsuarioID.setEditable(false);
    }

    public void iniciar() {
        frm.setTitle("Gestión de Facturas");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    private void limpiarCampos() {
        frm.txtIdFactura.setText("");
        frm.txtNumeroFactura.setText("");
        frm.txtClienteID.setText("");
        frm.txtSubTotalFactura.setText("");
        frm.txtDescuentoFactura.setText("");
        frm.txtIgvFactura.setText("");
        frm.txtTotalFactura.setText("");
        frm.cbxEstadoFactura.setSelectedIndex(0);
        frm.txtFechaFactura.setText(sdf.format(new Date()));
    }

    private void cargarFacturas() {
        try {
            List<ClaseFactura> facturas = modConsulta.obtenerTodas();
            DefaultTableModel modelo = (DefaultTableModel) frm.tblFactura.getModel();
            modelo.setRowCount(0);

            for (ClaseFactura factura : facturas) {
                modelo.addRow(new Object[]{
                    factura.getId_facturas(),
                    factura.getNumero_factura(),
                    sdf.format(factura.getFecha_factura()),
                    factura.getCliente_id(),
                    factura.getSubtotal(),
                    factura.getDescuento(),
                    factura.getIgv(),
                    factura.getTotal(),
                    factura.getEstado()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar facturas: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarFactura() {
        int fila = frm.tblFactura.getSelectedRow();
        if (fila >= 0) {
            frm.txtIdFactura.setText(frm.tblFactura.getValueAt(fila, 0).toString());
            frm.txtNumeroFactura.setText(frm.tblFactura.getValueAt(fila, 1).toString());
            frm.txtFechaFactura.setText(frm.tblFactura.getValueAt(fila, 2).toString());
            frm.txtClienteID.setText(frm.tblFactura.getValueAt(fila, 3).toString());
            frm.txtSubTotalFactura.setText(frm.tblFactura.getValueAt(fila, 4).toString());
            frm.txtDescuentoFactura.setText(frm.tblFactura.getValueAt(fila, 5).toString());
            frm.txtIgvFactura.setText(frm.tblFactura.getValueAt(fila, 6).toString());
            frm.txtTotalFactura.setText(frm.tblFactura.getValueAt(fila, 7).toString());
            frm.cbxEstadoFactura.setSelectedItem(frm.tblFactura.getValueAt(fila, 8).toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnNuevoFactura) {
            guardarFactura();
        } else if (e.getSource() == frm.btnLeerFactura) {
            seleccionarFactura();
        } else if (e.getSource() == frm.btnModificarFactura) {
            actualizarFactura();
        } else if (e.getSource() == frm.btnEliminarFactura) {
            eliminarFactura();
        }
    }

    private void guardarFactura() {
        try {
            mod.setNumero_factura(frm.txtNumeroFactura.getText());
            mod.setCliente_id(Integer.parseInt(frm.txtClienteID.getText()));
            mod.setSubtotal(Double.parseDouble(frm.txtSubTotalFactura.getText()));
            mod.setDescuento(Double.parseDouble(frm.txtDescuentoFactura.getText()));
            mod.setIgv(Double.parseDouble(frm.txtIgvFactura.getText()));
            mod.setTotal(Double.parseDouble(frm.txtTotalFactura.getText()));
            mod.setEstado(frm.cbxEstadoFactura.getSelectedItem().toString());
            mod.setUsuario_id(usuarioId);

            Date fecha = sdf.parse(frm.txtFechaFactura.getText());
            mod.setFecha_factura(new java.sql.Timestamp(fecha.getTime()));

            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Factura registrada");
                limpiarCampos();
                cargarFacturas();
            }
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy HH:mm:ss");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void actualizarFactura() {
        try {
            mod.setId_facturas(Integer.parseInt(frm.txtIdFactura.getText()));
            mod.setNumero_factura(frm.txtNumeroFactura.getText());
            mod.setCliente_id(Integer.parseInt(frm.txtClienteID.getText()));
            mod.setSubtotal(Double.parseDouble(frm.txtSubTotalFactura.getText()));
            mod.setDescuento(Double.parseDouble(frm.txtDescuentoFactura.getText()));
            mod.setIgv(Double.parseDouble(frm.txtIgvFactura.getText()));
            mod.setTotal(Double.parseDouble(frm.txtTotalFactura.getText()));
            mod.setEstado(frm.cbxEstadoFactura.getSelectedItem().toString());
            mod.setUsuario_id(usuarioId);

            Date fecha = sdf.parse(frm.txtFechaFactura.getText());
            mod.setFecha_factura(new java.sql.Timestamp(fecha.getTime()));

            if (modConsulta.modificar(mod)) {
                JOptionPane.showMessageDialog(null, "Factura actualizada");
                cargarFacturas();
            }
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy HH:mm:ss");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void eliminarFactura() {
        int id = Integer.parseInt(frm.txtIdFactura.getText());
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "¿Anular esta factura?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (modConsulta.eliminar(id)) {
                JOptionPane.showMessageDialog(null, "Factura anulada");
                limpiarCampos();
                cargarFacturas();
            }
        }
    }
}