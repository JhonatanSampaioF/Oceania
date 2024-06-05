package fiap.tds.models;

import java.sql.Date;

public class Pedido extends _BaseEntity {

    private Date dt_pedido;
    private String status_pedido;
    private Double valor_total;
    private String metodo_pgto;
    private int fk_cliente;
    private int quantidade;
    private Double subtotal;
    private int fk_pedido;
    private int fk_prod;

    public Pedido() {
    }

    public Pedido(int id, Date dt_pedido, String status_pedido, Double valor_total, String metodo_pgto, int fk_cliente, int quantidade, Double subtotal, int fk_pedido, int fk_prod) {
        super(id);
        this.dt_pedido = dt_pedido;
        this.status_pedido = status_pedido;
        this.valor_total = valor_total;
        this.metodo_pgto = metodo_pgto;
        this.fk_cliente = fk_cliente;
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.fk_pedido = fk_pedido;
        this.fk_prod = fk_prod;
    }

    public Date getDt_pedido() {
        return dt_pedido;
    }

    public void setDt_pedido(Date dt_pedido) {
        this.dt_pedido = dt_pedido;
    }

    public String getStatus_pedido() {
        return status_pedido;
    }

    public void setStatus_pedido(String status_pedido) {
        this.status_pedido = status_pedido;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public String getMetodo_pgto() {
        return metodo_pgto;
    }

    public void setMetodo_pgto(String metodo_pgto) {
        this.metodo_pgto = metodo_pgto;
    }

    public int getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(int fk_cliente) {
        this.fk_cliente = fk_cliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public int getFk_pedido() {
        return fk_pedido;
    }

    public void setFk_pedido(int fk_pedido) {
        this.fk_pedido = fk_pedido;
    }

    public int getFk_prod() {
        return fk_prod;
    }

    public void setFk_prod(int fk_prod) {
        this.fk_prod = fk_prod;
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "dt_pedido=" + dt_pedido +
            ", status_pedido='" + status_pedido + '\'' +
            ", valor_total=" + valor_total +
            ", metodo_pgto='" + metodo_pgto + '\'' +
            ", fk_cliente=" + fk_cliente +
            ", quantidade=" + quantidade +
            ", subtotal=" + subtotal +
            ", fk_pedido=" + fk_pedido +
            ", fk_prod=" + fk_prod +
            "} " + super.toString();
    }
}
