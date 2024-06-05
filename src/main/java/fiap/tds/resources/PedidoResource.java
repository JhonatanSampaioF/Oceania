package fiap.tds.resources;

import fiap.tds.models.Pedido;
import fiap.tds.repositories.PedidoRepositoryOracle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("pedidos")
public class PedidoResource {

    PedidoRepositoryOracle pedidoRepo = new PedidoRepositoryOracle();


    @GET
    @Path("{id_pedido}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedido(@PathParam("id_pedido") int id_pedido) {
        var pedido = pedidoRepo.findById(id_pedido);
        if (pedido == null)
            return Response.status(404).entity("Pedido não encontrado").build();
        return Response.status(200).entity(pedido).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPedido(Pedido pedido) {
        if (pedido == null)
            return Response.status(400).entity("Pedido não pode ser nulo").build();
        pedidoRepo.create(pedido);
        return Response.status(201).entity(pedido).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPedidos() {
        List<Pedido> pedidos = pedidoRepo.readAll();
        return Response.status(200).entity(pedidos).build();
    }


    @PUT
    @Path("{id_pedido}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePedido(@PathParam("id_pedido") int id_pedido, Pedido pedido) {
        pedido.setId(id_pedido);
        pedidoRepo.update(pedido);
        return Response.status(Response.Status.OK).entity(pedido).build();
    }


    @DELETE
    @Path("{id_pedido}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePedido(@PathParam("id_pedido") int id_pedido) {
        pedidoRepo.delete(id_pedido);
        return Response.status(204).build();
    }

}

