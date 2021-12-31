package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ExerciseDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ProgramDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.ProgramBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Exercise;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Program;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("program")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ProgramService {
    @EJB
    ProgramBean programBean;

    private ProgramDTO toDTO (Program program)
    {
        ProgramDTO programDTO = new ProgramDTO(
                program.getCode(),
                program.getName(),
                program.getDescProgram()
        );
        programDTO.setExercises(exercisetoDTOs(program.getExercises()));
        return programDTO;
    }

    private List<ProgramDTO> toDTOs(List<Program> programs)
    {
        return programs.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ExerciseDTO exercisetoDTO (Exercise exercise)
    {
        return new ExerciseDTO(
                exercise.getCode(),
                exercise.getName(),
                exercise.getDescExercise()
        );
    }

    private List<ExerciseDTO> exercisetoDTOs(List<Exercise> exercises)
    {
        return exercises.stream().map(this::exercisetoDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProgramDTO> getAllPrograms()
    {
        return toDTOs(programBean.getAllPrograms());
    }

    @GET
    @Path("{code}")
    public Response getProgramDetails(@PathParam("code") int code)
    {
        Program program = programBean.findProgram(code);
        if(program!=null)
            return Response.ok(toDTO(program)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRGRAM").build();
    }

    @GET
    @Path("{code}/exercises")
    //@RolesAllowed({"Admin, PatientUser"})
    public Response getProgramExercises(@PathParam("code") int code) {
        Program program = programBean.findProgram(code);
        if(program!=null)
        {
            List<ExerciseDTO> dtos = exercisetoDTOs(program.getExercises());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PATIENT").build();
    }

    @POST
    @Path("/")
    public Response create (ProgramDTO programDTO) throws MyConstraintViolationException, MyEntityExistsException {
        programBean.create(programDTO.getCode(),
                programDTO.getName(),
                programDTO.getDescProgram());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    public Response remove (@PathParam("code") int code) throws MyEntityNotFoundException {
        programBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    public Response update (@PathParam("code") int code, ProgramDTO programDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        programBean.update(code, programDTO.getName(), programDTO.getDescProgram());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{code}")
    public Response consult(@PathParam("code") int code)
    {
        Program program = programBean.findProgram(code);
        if(program!=null)
            return Response.ok(toDTO(program)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PROGRAM").build();
    }

    @POST
    @Path("/{code}/exercises")
    public Response enrollExercise (@PathParam("code") int code, ExerciseDTO exerciseDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        programBean.enrollExercise(code, exerciseDTO.getCode());
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}/exercises")
    public Response unrollExercise (@PathParam("code") int code, ExerciseDTO exerciseDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        programBean.unrollExercise(code, exerciseDTO.getCode());
        return Response.status(Response.Status.OK).build();
    }
}
