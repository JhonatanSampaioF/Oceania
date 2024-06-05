package fiap.tds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.tds.models.CepApiResponse;
import fiap.tds.utils.HttpClientUtil;

public class CepApi {
    private static final String TOKEN_URL = "https://h-apigateway.conectagov.estaleiro.serpro.gov.br/oauth2/jwt-token";
    private static final String API_URL = "https://h-apigateway.conectagov.estaleiro.serpro.gov.br/api-cep/v1/consulta/cep/%s";

    public static CepApiResponse getEnderecoPorCep(String cep, String token) throws Exception {
        String url = String.format(API_URL, cep);
        String response = HttpClientUtil.sendGET(url, token);
        return new ObjectMapper().readValue(response, CepApiResponse.class);
    }
}
