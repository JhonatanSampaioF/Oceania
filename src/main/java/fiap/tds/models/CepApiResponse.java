package fiap.tds.models;

public class CepApiResponse {
    private String cep;
    private String tipoCep;
    private String subTipoCep;
    private String uf;
    private String cidade;
    private String bairro;
    private String endereco;
    private String complemento;
    private String codigoIBGE;

    // Getters and Setters
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoCep() {
        return tipoCep;
    }

    public void setTipoCep(String tipoCep) {
        this.tipoCep = tipoCep;
    }

    public String getSubTipoCep() {
        return subTipoCep;
    }

    public void setSubTipoCep(String subTipoCep) {
        this.subTipoCep = subTipoCep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }
}
