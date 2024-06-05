package fiap.tds.models;

public class DetalhesPedido {

    private int quantidade;
    private Double subtotal;
    private int fk_pedido;
    private int fk_prod;

    public DetalhesPedido() {
    }

    public DetalhesPedido(int quantidade, Double subtotal, int fk_pedido, int fk_prod) {
        this.quantidade = quantidade;
        this.subtotal = subtotal;
        this.fk_pedido = fk_pedido;
        this.fk_prod = fk_prod;
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
        return "DetalhesPedido{" +
            "quantidade=" + quantidade +
            ", subtotal=" + subtotal +
            ", fk_pedido=" + fk_pedido +
            ", fk_prod=" + fk_prod +
            '}';
    }
}
