package fiap.tds.resources;

import fiap.tds.models.Loja;
import fiap.tds.repositories.LojaRepositoryOracle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("lojas")
public class LojaResource {

    LojaRepositoryOracle lojaRepo = new LojaRepositoryOracle();


    @GET
    @Path("{id_loja}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoja(@PathParam("id_loja") int id_loja) {
        var loja = lojaRepo.findById(id_loja);
        if (loja == null)
            return Response.status(404).entity("Loja não encontrado").build();
        return Response.status(200).entity(loja).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLoja(Loja loja) {
        if (loja == null)
            return Response.status(400).entity("Loja não pode ser nulo").build();
        lojaRepo.create(loja);
        return Response.status(201).entity(loja).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLojas() {
        List<Loja> lojas = lojaRepo.readAll();
        return Response.status(200).entity(lojas).build();
    }


    @PUT
    @Path("{id_loja}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLoja(@PathParam("id_loja") int id_loja, Loja loja) {
        loja.setId(id_loja);
        lojaRepo.update(loja);
        return Response.status(Response.Status.OK).entity(loja).build();
    }


    @DELETE
    @Path("{id_loja}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLoja(@PathParam("id_loja") int id_loja) {
        lojaRepo.delete(id_loja);
        return Response.status(204).build();
    }

}

