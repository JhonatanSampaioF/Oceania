package fiap.tds.models;

import java.sql.Date;

public class Lojista extends _BaseEntity {

    private String nm_lojista;
    private String cnpj;
    private String email;
    private String senha;
    private Date dt_criacao;

    public Lojista() {
    }

    public Lojista(int id, String nm_lojista, String cnpj, String email, String senha, Date dt_criacao) {
        super(id);
        this.nm_lojista = nm_lojista;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.dt_criacao = dt_criacao;
    }

    public String getNm_lojista() {
        return nm_lojista;
    }

    public void setNm_lojista(String nm_lojista) {
        this.nm_lojista = nm_lojista;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDt_criacao() {
        return dt_criacao;
    }

    public void setDt_criacao(Date dt_criacao) {
        this.dt_criacao = dt_criacao;
    }

    @Override
    public String toString() {
        return "Lojista{" +
            "nm_lojista='" + nm_lojista + '\'' +
            ", cnpj='" + cnpj + '\'' +
            ", email='" + email + '\'' +
            ", senha='" + senha + '\'' +
            ", dt_criacao=" + dt_criacao +
            "} " + super.toString();
    }
}
