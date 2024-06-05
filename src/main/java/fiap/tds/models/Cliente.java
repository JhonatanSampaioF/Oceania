package fiap.tds.models;

import java.sql.Date;

public class Cliente extends _BaseEntity {

    private String nm_clie;
    private int cpf;
    private String email;
    private String senha;
    private Date dt_nasc;
    private Date dt_criacao;

    public Cliente() {
    }

    public Cliente(int id, String nm_clie, int cpf, String email, String senha, Date dt_nasc, Date dt_criacao) {
        super(id);
        this.nm_clie = nm_clie;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dt_nasc = dt_nasc;
        this.dt_criacao = dt_criacao;
    }

    public String getNm_clie() {
        return nm_clie;
    }

    public void setNm_clie(String nm_clie) {
        this.nm_clie = nm_clie;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
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

    public Date getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(Date dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    public Date getDt_criacao() {
        return dt_criacao;
    }

    public void setDt_criacao(Date dt_criacao) {
        this.dt_criacao = dt_criacao;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "nm_clie='" + nm_clie + '\'' +
            ", cpf=" + cpf +
            ", email='" + email + '\'' +
            ", senha='" + senha + '\'' +
            ", dt_nasc=" + dt_nasc +
            ", dt_criacao=" + dt_criacao +
            "} " + super.toString();
    }
}
