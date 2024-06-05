package fiap.tds.models;

public class Endereco extends _BaseEntity {

    private int cep;
    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String info_adicionais;
    private String tipo_end;
    private int fk_cliente;

    public Endereco() {
    }

    public Endereco(int id, int cep, String logradouro, int numero, String bairro, String cidade, String estado, String pais, String info_adicionais, String tipo_end, int fk_cliente) {
        super(id);
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.info_adicionais = info_adicionais;
        this.tipo_end = tipo_end;
        this.fk_cliente = fk_cliente;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getInfo_adicionais() {
        return info_adicionais;
    }

    public void setInfo_adicionais(String info_adicionais) {
        this.info_adicionais = info_adicionais;
    }

    public String getTipo_end() {
        return tipo_end;
    }

    public void setTipo_end(String tipo_end) {
        this.tipo_end = tipo_end;
    }

    public int getFk_cliente() {
        return fk_cliente;
    }

    public void setFk_cliente(int fk_cliente) {
        this.fk_cliente = fk_cliente;
    }

    @Override
    public String toString() {
        return "Endereco{" +
            "cep=" + cep +
            ", logradouro='" + logradouro + '\'' +
            ", numero=" + numero +
            ", bairro='" + bairro + '\'' +
            ", cidade='" + cidade + '\'' +
            ", estado='" + estado + '\'' +
            ", pais='" + pais + '\'' +
            ", info_adicionais='" + info_adicionais + '\'' +
            ", tipo_end='" + tipo_end + '\'' +
            ", fk_cliente=" + fk_cliente +
            "} " + super.toString();
    }
}
