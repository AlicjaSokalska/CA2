package resource;


import javax.ws.rs.*;

import entities.Emission;
import service.EmissionService;

import java.util.List;
@Path("/emissions")
public class EmissionResource {

    private final EmissionService emissionService;

    public EmissionResource() {
        this.emissionService = new EmissionService();
    }
//view all
    @GET
    @Path("/emissions")
    @Produces("application/json")
    public List<Emission> getAllEmissions() {
        return emissionService.getAllEmissions();
    }
//view
    @GET
    @Path("/emission/{id}")
    @Produces("application/json")
    public Emission getEmissionById(@PathParam("id") int id) {
        return emissionService.getEmissionById(id);
    }
//create
    @POST
    @Path("/emission")
    @Consumes("application/json")
    public void createEmission(Emission emission) {
        emissionService.createEmission(emission);
    }
//update
    @PUT
    @Path("/emission/{id}")
    @Consumes("application/json")
    public void updateEmission(@PathParam("id") int id, Emission updatedEmission) {
        emissionService.updateEmission(id, updatedEmission);
    }
//delet
    @DELETE
    @Path("/emission/{id}")
    public void deleteEmission(@PathParam("id") int id) {
        emissionService.deleteEmission(id);
    }
//cat
    @GET
    @Path("/emission/category/{category}")
    @Produces("application/json")
    public Emission getEmissionByCategory(@PathParam("category") String category) {
        return emissionService.getEmissionByCategory(category);
    }
//gas
    @GET
    @Path("/emission/gasunit/{gasUnit}")
    @Produces("application/json")
    public List<Emission> getEmissionsByGasUnit(@PathParam("gasUnit") String gasUnit) {
        return emissionService.getEmissionsByGasUnit(gasUnit);
    }
    //value
}
