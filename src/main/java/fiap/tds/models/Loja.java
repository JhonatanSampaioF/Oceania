package fiap.tds.models;

public class Loja extends _BaseEntity {

    private String nm_loja;
    private int fk_lojista;

    public Loja() {
    }

    public Loja(int id, String nm_loja, int fk_lojista) {
        super(id);
        this.nm_loja = nm_loja;
        this.fk_lojista = fk_lojista;
    }

    public String getNm_loja() {
        return nm_loja;
    }

    public void setNm_loja(String nm_loja) {
        this.nm_loja = nm_loja;
    }

    public int getFk_lojista() {
        return fk_lojista;
    }

    public void setFk_lojista(int fk_lojista) {
        this.fk_lojista = fk_lojista;
    }

    @Override
    public String toString() {
        return "Loja{" +
            "nm_loja='" + nm_loja + '\'' +
            ", fk_lojista=" + fk_lojista +
            "} " + super.toString();
    }
}
