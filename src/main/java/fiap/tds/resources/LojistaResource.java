package fiap.tds.resources;

import fiap.tds.models.Lojista;
import fiap.tds.repositories.LojistaRepositoryOracle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("lojistas")
public class LojistaResource {

    LojistaRepositoryOracle lojistaRepo = new LojistaRepositoryOracle();


    @GET
    @Path("{id_lojista}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLojista(@PathParam("id_lojista") int id_lojista) {
        var lojista = lojistaRepo.findById(id_lojista);
        if (lojista == null)
            return Response.status(404).entity("Lojista não encontrado").build();
        return Response.status(200).entity(lojista).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLojista(Lojista lojista) {
        if (lojista == null)
            return Response.status(400).entity("Lojista não pode ser nulo").build();
        lojistaRepo.create(lojista);
        return Response.status(201).entity(lojista).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLojistas() {
        List<Lojista> lojistas = lojistaRepo.readAll();
        return Response.status(200).entity(lojistas).build();
    }


    @PUT
    @Path("{id_lojista}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLojista(@PathParam("id_lojista") int id_lojista, Lojista lojista) {
        lojista.setId(id_lojista);
        lojistaRepo.update(lojista);
        return Response.status(Response.Status.OK).entity(lojista).build();
    }


    @DELETE
    @Path("{id_lojista}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteLojista(@PathParam("id_lojista") int id_lojista) {
        lojistaRepo.delete(id_lojista);
        return Response.status(204).build();
    }

}

