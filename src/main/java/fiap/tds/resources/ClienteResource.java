package fiap.tds.resources;

import fiap.tds.models.Cliente;
import fiap.tds.repositories.ClienteRepositoryOracle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import static fiap.tds.Main.LOGGER;

@Path("clientes")
public class ClienteResource {

    ClienteRepositoryOracle clienteRepo = new ClienteRepositoryOracle();

    @GET
    @Path("{id_clie}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("id_clie") int id_clie) {
        LOGGER.info("Recebida requisição GET para /clientes/{}", id_clie);
        var cliente = clienteRepo.findById(id_clie);
        if (cliente == null)
            return Response.status(404).entity("Cliente não encontrado").build();
        return Response.status(200).entity(cliente).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCliente(Cliente cliente) {
        LOGGER.info("Recebida requisição POST para /clientes com body: {}", cliente);
        if (cliente == null)
            return Response.status(400).entity("Cliente não pode ser nulo").build();
        try {
            clienteRepo.create(cliente);
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        return Response.status(201).entity(cliente).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClientes() {
        LOGGER.info("Recebida requisição GET para /clientes");
        List<Cliente> clientes = clienteRepo.readAll();
        return Response.status(200).entity(clientes).build();
    }

    @PUT
    @Path("{id_clie}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCliente(@PathParam("id_clie") int id_clie, Cliente cliente) {
        LOGGER.info("Recebida requisição PUT para /clientes/{} com body: {}", id_clie, cliente);
        cliente.setId(id_clie);
        try {
            clienteRepo.update(cliente);
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(cliente).build();
    }

    @DELETE
    @Path("{id_clie}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCliente(@PathParam("id_clie") int id_clie) {
        LOGGER.info("Recebida requisição DELETE para /clientes/{}", id_clie);
        clienteRepo.delete(id_clie);
        return Response.status(204).build();
    }
}