package fiap.tds.models;

import java.sql.Date;

public class Pedido extends _BaseEntity {

    private Date dt_pedido;
    private String status_pedido;
    private Double valor_total;
    private String metodo_pgto;
    private int fk_cliente;


    public Pedido() {
    }

    public Pedido(int id, Date dt_pedido, String status_pedido, Double valor_total, String metodo_pgto, int fk_cliente) {
        super(id);
        this.dt_pedido = dt_pedido;
        this.status_pedido = status_pedido;
        this.valor_total = valor_total;
        this.metodo_pgto = metodo_pgto;
        this.fk_cliente = fk_cliente;
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

    @Override
    public String toString() {
        return "Pedido{" +
            "dt_pedido=" + dt_pedido +
            ", status_pedido='" + status_pedido + '\'' +
            ", valor_total=" + valor_total +
            ", metodo_pgto='" + metodo_pgto + '\'' +
            ", fk_cliente=" + fk_cliente +
            "} " + super.toString();
    }
}
