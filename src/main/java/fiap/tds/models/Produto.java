package fiap.tds.models;

import java.sql.Date;

public class Produto extends _BaseEntity {

    private String nm_prod;
    private String desc_prod;
    private Double preco;
    private int estoque;
    private String categoria;
    private Date dt_criacao_prod;
    private int fk_loja;

    public Produto() {
    }

    public Produto(int id, String nm_prod, String desc_prod, Double preco, int estoque, String categoria, Date dt_criacao_prod, int fk_loja) {
        super(id);
        this.nm_prod = nm_prod;
        this.desc_prod = desc_prod;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.dt_criacao_prod = dt_criacao_prod;
        this.fk_loja = fk_loja;
    }

    public String getNm_prod() {
        return nm_prod;
    }

    public void setNm_prod(String nm_prod) {
        this.nm_prod = nm_prod;
    }

    public String getDesc_prod() {
        return desc_prod;
    }

    public void setDesc_prod(String desc_prod) {
        this.desc_prod = desc_prod;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getDt_criacao_prod() {
        return dt_criacao_prod;
    }

    public void setDt_criacao_prod(Date dt_criacao_prod) {
        this.dt_criacao_prod = dt_criacao_prod;
    }

    public int getFk_loja() {
        return fk_loja;
    }

    public void setFk_loja(int fk_loja) {
        this.fk_loja = fk_loja;
    }

    @Override
    public String toString() {
        return "Produto{" +
            "nm_prod='" + nm_prod + '\'' +
            ", desc_prod='" + desc_prod + '\'' +
            ", preco=" + preco +
            ", estoque=" + estoque +
            ", categoria='" + categoria + '\'' +
            ", dt_criacao_prod=" + dt_criacao_prod +
            ", fk_loja=" + fk_loja +
            "} " + super.toString();
    }
}
