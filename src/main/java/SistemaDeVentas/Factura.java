package SistemaDeVentas;

import java.sql.*;
import java.util.List;

public class Factura {
    private String clienteDocumento;
    private String clienteNombre;
    private long mascotaId;
    private String mascotaNombre;
    private String mascotaEspecie;
    private String mascotaEdad;
    private String mascotaGenero;
    private String metodoPago;
    private double montoRecibido;
    private double cambio;
    private double total;
    private List<DetalleFactura> detalles;

    public Factura(String clienteDocumento, String clienteNombre, long mascotaId, String mascotaNombre, 
                   String mascotaEspecie, String mascotaEdad, String mascotaGenero, 
                   String metodoPago, double montoRecibido, double cambio, 
                   double total, List<DetalleFactura> detalles) {
        this.clienteDocumento = clienteDocumento;
        this.clienteNombre = clienteNombre;
        this.mascotaId = mascotaId;
        this.mascotaNombre = mascotaNombre;
        this.mascotaEspecie = mascotaEspecie;
        this.mascotaEdad = mascotaEdad;
        this.mascotaGenero = mascotaGenero;
        this.metodoPago = metodoPago;
        this.montoRecibido = montoRecibido;
        this.cambio = cambio;
        this.total = total;
        this.detalles = detalles;
    }

    public boolean guardarEnBD(Connection conn) {
        String sqlVenta = "INSERT INTO ventas (cliente_documento, mascota_id, metodo_pago, monto_recibido, cambio, total) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        String sqlDetalle = "INSERT INTO detalle_ventas (venta_id, tipo_item, item_id, descripcion, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
 
        
        String sqlStock = "UPDATE inventario SET stock = stock - ? WHERE id = ?";

        try {
            conn.setAutoCommit(false); // Transacción atómica

            int idVenta = 0;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlVenta)) {
                pstmt.setString(1, clienteDocumento);
                pstmt.setLong(2, mascotaId);
                pstmt.setString(3, metodoPago);
                pstmt.setDouble(4, montoRecibido);
                pstmt.setDouble(5, cambio);
                pstmt.setDouble(6, total);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    idVenta = rs.getInt("id");
                }
            }

            for (DetalleFactura d : detalles) {
                try (PreparedStatement pstmtD = conn.prepareStatement(sqlDetalle)) {
                    pstmtD.setInt(1, idVenta);
                    pstmtD.setString(2, d.getTipoItem());
                    pstmtD.setLong(3, d.getItemId());
                    pstmtD.setString(4, d.getDescripcion());
                    pstmtD.setInt(5, d.getCantidad());
                    pstmtD.setDouble(6, d.getPrecioUnitario());
                    pstmtD.setDouble(7, d.getSubtotal());
                    pstmtD.executeUpdate();
                }

                if (d.getTipoItem().equals("Producto")) {
                    try (PreparedStatement pstmtS = conn.prepareStatement(sqlStock)) {
                        pstmtS.setInt(1, d.getCantidad());
                        pstmtS.setLong(2, d.getItemId());
                        pstmtS.executeUpdate();
                    }
                }
            }

            conn.commit();
            conn.setAutoCommit(true);
            imprimirComprobante(idVenta);
            return true;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            System.out.println("Error al registrar la factura en Supabase: " + e.getMessage());
            return false;
        }
    }

    public void imprimirComprobante(int idVenta) {
        System.out.println("\n==================================================================");
        System.out.println("                      FACTURA DE VENTA N° " + idVenta);
        System.out.println("==================================================================");
        System.out.println("Cliente : " + clienteNombre + " (Doc: " + clienteDocumento + ")");
        System.out.println("Mascota : " + mascotaNombre + " | Especie: " + mascotaEspecie + " | Edad: " + mascotaEdad + " | Género: " + mascotaGenero);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-5s %-38s %-10s %s\n", "CANT", "DESCRIPCIÓN", "PRECIO U.", "SUBTOTAL");
        System.out.println("------------------------------------------------------------------");
        for (DetalleFactura d : detalles) {
            System.out.printf("%-5d %-38s $%-9.2f $%.2f\n", d.getCantidad(), d.getDescripcion(), d.getPrecioUnitario(), d.getSubtotal());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.printf("TOTAL A PAGAR: $%.2f\n", total);
        System.out.println("Método de Pago: " + metodoPago);
        if (metodoPago.equals("Efectivo")) {
            System.out.printf("Monto Recibido: $%.2f\n", montoRecibido);
            System.out.printf("Cambio        : $%.2f\n", cambio);
        }
        System.out.println("==================================================================");
        System.out.println("                 ¡Gracias por su compra!");
        System.out.println("==================================================================\n");
    }
}

