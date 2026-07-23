package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaCliente;
import bodega_myc_.Modelo.ClaseCliente;
import bodega_myc_.Vista.ReportesClientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReportesClientesController implements ActionListener {
    private final ClaseConsultaCliente modConsulta;
    private final ReportesClientes frm;
    
    public ReportesClientesController(ClaseConsultaCliente modConsulta, ReportesClientes frm) {
        this.modConsulta = modConsulta;
        this.frm = frm;
        this.frm.BtnReporterClientesBuscar.addActionListener(this);
    }
    
    public void iniciar() {
        frm.setTitle("Reportes de Clientes");
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio clave
        frm.setVisible(true);
    }
    
    private void cargarReporteClientes() {
        try {
            List<ClaseCliente> clientes = modConsulta.obtenerReporteClientes();
            DefaultTableModel modelo = (DefaultTableModel) frm.TablaReporteClientes.getModel();
            modelo.setRowCount(0);
            
            for (ClaseCliente cliente : clientes) {
                modelo.addRow(new Object[]{
                    cliente.getDni(),
                    cliente.getNombre_cliente(),
                    cliente.getFecha_registro(),
                    cliente.getCantidadCompras(),
                    cliente.getMontoTotalGastado()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm, "Error al cargar reporte: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.BtnReporterClientesBuscar) {
            cargarReporteClientes();
        }
    }
}