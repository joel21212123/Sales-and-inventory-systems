package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.*;
import bodega_myc_.Modelo.*;
import bodega_myc_.Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalController implements ActionListener {
    private final MENU_PRINCIPAL frmMenu;
    private final int usuarioId;

    public MenuPrincipalController(MENU_PRINCIPAL frmMenu, int usuarioId) {
        this.frmMenu = frmMenu;
        this.usuarioId = usuarioId;
        
        // Asignar listeners a los elementos del menú
        frmMenu.clientesJd.addActionListener(this);
        frmMenu.Productojd.addActionListener(this);
        frmMenu.ProveedoresJd.addActionListener(this);
        frmMenu.detalleFactujd.addActionListener(this);
        frmMenu.comprasJD.addActionListener(this);
        frmMenu.SalirJd.addActionListener(this);
        frmMenu.jdFactura.addActionListener(this); 
        frmMenu.InventarioJd.addActionListener(this);
        // Reportes
        frmMenu.jdReportesClientes.addActionListener(this);
        frmMenu.jdReportesCompras.addActionListener(this);
        frmMenu.jdReporteVentas.addActionListener(this);
    }
    
    public void iniciar() {
        frmMenu.setLocationRelativeTo(null);
        frmMenu.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmMenu.clientesJd) {
            abrirClientes();
        } 
        else if (e.getSource() == frmMenu.Productojd) {
            abrirProductos();
        }
        else if (e.getSource() == frmMenu.ProveedoresJd) {
            abrirProveedores();
        }
        else if (e.getSource() == frmMenu.comprasJD) {
            abrirCompras();
        }
        else if (e.getSource() == frmMenu.detalleFactujd) {
            abrirDetalleFactura();
        }

        else if (e.getSource() == frmMenu.SalirJd) {
            System.exit(0);
        }
         else if (e.getSource() == frmMenu.jdFactura) {
            abrirFacturas(); 
        }
        else if (e.getSource() == frmMenu.InventarioJd) {
            abrirMovimientoInventario(); 
        }
        // Reportes
        else if (e.getSource() == frmMenu.jdReportesClientes) {
            abrirReporteClientes();
        }
        else if (e.getSource() == frmMenu.jdReportesCompras) {
            abrirReporteCompras();
        }
        else if (e.getSource() == frmMenu.jdReporteVentas) {
            abrirReporteVentas();
        }
    }
    // Métodos para abrir los módulos principales
    private void abrirClientes() {
        Cliente vistaClientes = new Cliente(frmMenu, true);
        ClaseCliente modeloCliente = new ClaseCliente();
        ClaseConsultaCliente consultaCliente = new ClaseConsultaCliente();
        
        // Crear el controlador
        ClienteController controlador = new ClienteController(
            modeloCliente, 
            consultaCliente, 
            vistaClientes
        );
        
        // Mostrar el formulario
        vistaClientes.setVisible(true);
    }
    
    private void abrirProductos() {
        PRODUCTO vistaProductos = new PRODUCTO(frmMenu, true);
        ClaseProducto modeloProducto = new ClaseProducto();
        ClaseConsultaProducto consultaProducto = new ClaseConsultaProducto();
        ProductoController controlador = new ProductoController(modeloProducto, consultaProducto, vistaProductos);
        controlador.iniciar();
    }
    
    private void abrirProveedores() {
        PROVEEDORES vistaProveedores = new PROVEEDORES(frmMenu, true);
        ClaseProveedor modeloProveedor = new ClaseProveedor();
        ClaseConsultaProveedores consultaProveedor = new ClaseConsultaProveedores();
        ProveedorController controlador = new ProveedorController(modeloProveedor, consultaProveedor, vistaProveedores);
        controlador.iniciar();
    }
    
    private void abrirCompras() {
        COMPRAS vistaCompras = new COMPRAS(frmMenu, true);
        ClaseCompra modeloCompra = new ClaseCompra();
        ClaseConsultaCompras consultaCompras = new ClaseConsultaCompras();
        ComprasController controlador = new ComprasController(modeloCompra, consultaCompras, vistaCompras);
        controlador.setUsuarioId(usuarioId);
        controlador.iniciar();
    }
    
    private void abrirDetalleFactura() {
        DETALLE_FACTURA vistaDetalleFactura = new DETALLE_FACTURA(frmMenu, true);
        ClaseDetalleFactura modeloDetalleFactura = new ClaseDetalleFactura();
        ClaseConsultaDetalleFactura consultaDetalleFactura = new ClaseConsultaDetalleFactura();
        DetalleFacturaController controlador = new DetalleFacturaController(
            modeloDetalleFactura,
            consultaDetalleFactura,
            vistaDetalleFactura
        );
        controlador.iniciar();
    }
    
    
      private void abrirMovimientoInventario() {
        MOVIMIENTO_INVENTARIO vistaMovimiento = new MOVIMIENTO_INVENTARIO(frmMenu, true);
        ClaseMovimientoInventario modeloMovimiento = new ClaseMovimientoInventario();
        ClaseConsultaMovimientosInventario consultaMovimiento = new ClaseConsultaMovimientosInventario();
        
        MovimientoInventarioController controlador = new MovimientoInventarioController(
            modeloMovimiento,
            consultaMovimiento,
            vistaMovimiento,
            usuarioId
        );
        
        vistaMovimiento.setVisible(true);
    }
    
    
    private void abrirFacturas() {
        FACTURA vistaFacturas = new FACTURA(frmMenu, true);
        ClaseFactura modeloFactura = new ClaseFactura();
        ClaseConsultaFactura consultaFactura = new ClaseConsultaFactura();
        
        FacturaController controlador = new FacturaController(
            modeloFactura, 
            consultaFactura, 
            vistaFacturas
        );
        
        controlador.setUsuarioId(usuarioId);
        vistaFacturas.setVisible(true);
    }
    // Métodos para abrir los reportes
    private void abrirReporteClientes() {
        ReportesClientes vistaReporteClientes = new ReportesClientes();
        ClaseConsultaCliente consultaCliente = new ClaseConsultaCliente();
        ReportesClientesController controlador = new ReportesClientesController(consultaCliente, vistaReporteClientes);
        controlador.iniciar();
    }
    
    private void abrirReporteCompras() {
        ReportesCompras vistaReporteCompras = new ReportesCompras();
        ClaseConsultaCompras consultaCompras = new ClaseConsultaCompras();
        ReportesComprasController controlador = new ReportesComprasController(consultaCompras, vistaReporteCompras);
        controlador.iniciar();
    }
    
    private void abrirReporteVentas() {
        ReportesVentas vistaReporteVentas = new ReportesVentas();
        ClaseConsultaFactura consultaFactura = new ClaseConsultaFactura();
        ReportesVentasController controlador = new ReportesVentasController(consultaFactura, vistaReporteVentas);
        controlador.iniciar();
    }
    
}