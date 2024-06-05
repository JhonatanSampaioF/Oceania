package fiap.tds.resources;

import fiap.tds.models.CepApiResponse;
import fiap.tds.models.Endereco;
import fiap.tds.repositories.EnderecoRepositoryOracle;
import fiap.tds.services.CepApi;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("enderecos")
public class EnderecoResource {

    EnderecoRepositoryOracle enderecoRepo = new EnderecoRepositoryOracle();


    @GET
    @Path("{id_end}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEndereco(@PathParam("id_end") int id_end) {
        var endereco = enderecoRepo.findById(id_end);
        if (endereco == null)
            return Response.status(404).entity("Endereço não encontrado").build();
        return Response.status(200).entity(endereco).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEndereco(Endereco endereco) {
        if (endereco == null)
            return Response.status(400).entity("Endereço não pode ser nulo").build();
        enderecoRepo.create(endereco);
        return Response.status(201).entity(endereco).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEnderecos() {
        List<Endereco> enderecos = enderecoRepo.readAll();
        return Response.status(200).entity(enderecos).build();
    }


    @PUT
    @Path("{id_end}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEndereco(@PathParam("id_end") int id_end, Endereco endereco) {
        endereco.setId(id_end);
        enderecoRepo.update(endereco);
        return Response.status(Response.Status.OK).entity(endereco).build();
    }


    @DELETE
    @Path("{id_end}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteEndereco(@PathParam("id_end") int id_end) {
        enderecoRepo.delete(id_end);
        return Response.status(204).build();
    }

    @GET
    @Path("cep/{cep}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnderecoPorCep(@PathParam("cep") String cep, @HeaderParam("Authorization") String token) {
        try {
            CepApiResponse cepData = CepApi.getEnderecoPorCep(cep, token);
            Endereco endereco = new Endereco();
            endereco.setCep(Integer.parseInt(cepData.getCep()));
            endereco.setLogradouro(cepData.getEndereco());
            endereco.setBairro(cepData.getBairro());
            endereco.setCidade(cepData.getCidade());
            endereco.setEstado(cepData.getUf());
            endereco.setPais("BR");
            endereco.setInfo_adicionais(cepData.getComplemento());

            // Aqui você pode definir outras propriedades do Endereço como necessário

            // Verifica se o endereço já existe
            Endereco existingEndereco = enderecoRepo.findById(endereco.getCep());
            if (existingEndereco == null) {
                enderecoRepo.create(endereco);
            } else {
                endereco.setId(existingEndereco.getId());
                enderecoRepo.update(endereco);
            }

            return Response.status(200).entity(endereco).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Erro ao buscar dados do CEP: " + e.getMessage()).build();
        }
    }

}