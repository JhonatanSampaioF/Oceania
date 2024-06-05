package fiap.tds.resources;

import fiap.tds.models.Cliente;
import fiap.tds.repositories.ClienteRepositoryOracle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("clientes")
public class ClienteResource {

    ClienteRepositoryOracle clienteRepo = new ClienteRepositoryOracle();


    @GET
    @Path("{id_clie}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("id_clie") int id_clie) {
        var cliente = clienteRepo.findById(id_clie);
        if (cliente == null)
            return Response.status(404).entity("Cliente não encontrado").build();
        return Response.status(200).entity(cliente).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCliente(Cliente cliente) {
        if (cliente == null)
            return Response.status(400).entity("Cliente não pode ser nulo").build();
        clienteRepo.create(cliente);
        return Response.status(201).entity(cliente).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClientes() {
        List<Cliente> clientes = clienteRepo.readAll();
        return Response.status(200).entity(clientes).build();
    }


    @PUT
    @Path("{id_clie}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCliente(@PathParam("id_clie") int id_clie, Cliente cliente) {
        cliente.setId(id_clie);
        clienteRepo.update(cliente);
        return Response.status(Response.Status.OK).entity(cliente).build();
    }


    @DELETE
    @Path("{id_clie}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCliente(@PathParam("id_clie") int id_clie) {
        clienteRepo.delete(id_clie);
        return Response.status(204).build();
    }

}

