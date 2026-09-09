package SistemaDeVentas;

public class DetalleFactura {

    private String tipoItem;
    private int itemId;
    private String descripcion;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleFactura(String tipoItem, int itemId, String descripcion, int cantidad, double precioUnitario) {
        this.tipoItem = tipoItem;
        this.itemId = itemId;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public String getTipoItem() { return tipoItem; }
    public int getItemId() { return itemId; }
    public String getDescripcion() { return descripcion; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }

}
