package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ExerciseDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.ExerciseBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Exercise;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.RescaleOp;
import java.util.List;
import java.util.stream.Collectors;

@Path("exercise")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ExerciseService {
    @EJB
    private ExerciseBean exerciseBean;

    private ExerciseDTO toDTO (Exercise exercise)
    {
        return new ExerciseDTO(
                exercise.getCode(),
                exercise.getName(),
                exercise.getDescExercise()
        );
    }

    private List<ExerciseDTO> toDTOs(List<Exercise> exercises)
    {
        return exercises.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"ProfHealthcare"})
    public List<ExerciseDTO> getAllExercises()
    {
        return toDTOs(exerciseBean.getAllExercise());
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response getExerciseDetails(@PathParam("code") int code)
    {
        Exercise exercise = exerciseBean.findExercise(code);
        if(exercise!=null)
            return Response.ok(toDTO(exercise)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_EXERCISE").build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"ProfHealthcare"})
    public Response create (ExerciseDTO exerciseDTO) throws MyConstraintViolationException, MyEntityExistsException {
        exerciseBean.create(exerciseDTO.getCode(),
                exerciseDTO.getName(),
                exerciseDTO.getDescExercise());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response remove (@PathParam("code") int code) throws MyEntityNotFoundException {
        exerciseBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response update (@PathParam("code") int code, ExerciseDTO exerciseDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        exerciseBean.update(code, exerciseDTO.getName(), exerciseDTO.getDescExercise());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response consult (@PathParam("code") int code){
        Exercise exercise = exerciseBean.findExercise(code);
        if(exercise!=null)
            return Response.ok(toDTO(exercise)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_EXERCISE").build();
    }
}
