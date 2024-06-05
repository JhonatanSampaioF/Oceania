package fiap.tds.resources;

import fiap.tds.models.DetalhesPedido;
import fiap.tds.repositories.DetalhesPedidoRepositoryOracle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("detalhesPedidos")
public class DetalhesPedidoResource {

    DetalhesPedidoRepositoryOracle detalhesPedidoRepo = new DetalhesPedidoRepositoryOracle();


    @GET
    @Path("{fk_pedido}/{fk_prod}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetalhesPedido(@PathParam("fk_pedido") int fk_pedido, @PathParam("fk_prod") int fk_prod) {
        var detalhesPedido = detalhesPedidoRepo.findById(fk_pedido, fk_prod);
        if (detalhesPedido == null)
            return Response.status(404).entity("Detalhes do pedido não encontrado").build();
        return Response.status(200).entity(detalhesPedido).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDetalhesPedido(DetalhesPedido detalhesPedido) {
        if (detalhesPedido == null)
            return Response.status(400).entity("Detalhes pedido não pode ser nulo").build();
        detalhesPedidoRepo.create(detalhesPedido);
        return Response.status(201).entity(detalhesPedido).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDetalhesPedidos() {
        List<DetalhesPedido> detalhesPedidos = detalhesPedidoRepo.readAll();
        return Response.status(200).entity(detalhesPedidos).build();
    }


    @PUT
    @Path("{fk_pedido}/{fk_prod}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDetalhesPedido(@PathParam("fk_pedido") int fk_pedido, @PathParam("fk_prod") int fk_prod, DetalhesPedido detalhesPedido) {
        detalhesPedido.setFk_pedido(fk_pedido);
        detalhesPedido.setFk_prod(fk_prod);
        detalhesPedidoRepo.update(detalhesPedido);
        return Response.status(Response.Status.OK).entity(detalhesPedido).build();
    }


    @DELETE
    @Path("{fk_pedido}/{fk_prod}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteDetalhesPedido(@PathParam("fk_pedido") int fk_pedido, @PathParam("fk_prod") int fk_prod) {
        detalhesPedidoRepo.delete(fk_pedido, fk_prod);
        return Response.status(204).build();
    }

}

