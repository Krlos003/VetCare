package ServicioDeEstetica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ServicioEstetica extends Servicio {
    private boolean incluyeBasico;
    private boolean incluyeAvanzado;
    private boolean incluyePremium;

    public ServicioEstetica(String nombreMascota, String tamanoMascota, boolean incluyeBasico, boolean incluyeAvanzado, boolean incluyePremium, String tipoServicio, double total) {
        super(tipoServicio, total, nombreMascota, tamanoMascota);
        this.incluyeBasico = incluyeBasico;
        this.incluyeAvanzado = incluyeAvanzado;
        this.incluyePremium = incluyePremium;
        this.tipoServicio = tipoServicio;
    }

    public static boolean guardarServicio (Connection conn, long mascotaId, boolean incluyeBasico, boolean incluyeAvanzado, boolean incluyePremium, String tipoServicio, double total) throws SQLException {
        String sql = "INSERT INTO servicios_estetica (mascota_id, incluye_basico, incluye_avanzado, incluye_premium) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, mascotaId);
            pstmt.setString(2, tipoServicio);
            pstmt.setDouble(3, total);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar el servicio de estética: " + e.getMessage());
            return false;
        }
    }

    @Override
    public double calcularPrecioTotal() {
        double total = super.calcularPrecioTotal();
        if (incluyeBasico) total += 20000;
        if (incluyeAvanzado) total += 30000;
        if (incluyePremium) total += 40000;
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + " (Servicio Básico: " + (incluyeBasico ? "Sí" : "No") + 
               ", Servicio Avanzado: " + (incluyeAvanzado ? "Sí" : "No") + 
               ", Servicio Premium: " + (incluyePremium ? "Sí" : "No") + ")";
    }
}