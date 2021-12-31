package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionExerciseDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionExerciseBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionExercise;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescription-exercises")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescriptionExerciseService {
    @EJB
    PrescriptionExerciseBean prescriptionExerciseBean;

    private PrescriptionExerciseDTO toDTO(PrescriptionExercise prescription) {
        String vigor;
        if (prescription.isVigor()) {
            vigor = "Está em vigor";
        } else {
            vigor = "Não está em vigor";
        }

        return new PrescriptionExerciseDTO(
                prescription.getCode(),
                prescription.getDuracao(),
                convertDatetoString(prescription.getInsertionDate()),
                vigor,
                prescription.getProgram().getCode(),
                prescription.getPatientUser().getUsername()
        );
    }

    private List<PrescriptionExerciseDTO> toDTOs(List<PrescriptionExercise> prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String convertDatetoString(Date data)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(data);
    }

    @GET
    @Path("{code}")
    public Response getPrescriptionDetails(@PathParam("code") int code) {
        PrescriptionExercise prescription = prescriptionExerciseBean.findPrescriptionExercise(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @POST
    @Path("/")
    public Response create(PrescriptionExerciseDTO prescriptionExerciseDTO) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        prescriptionExerciseBean.create(prescriptionExerciseDTO.getCode(),
                prescriptionExerciseDTO.getDuracao(),
                prescriptionExerciseDTO.getInsertionDate(),
                prescriptionExerciseDTO.getPatientUser_username(),
                prescriptionExerciseDTO.getProgramCode());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    public Response remove(@PathParam("code") int code) throws MyEntityNotFoundException {
        prescriptionExerciseBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    public Response update(@PathParam("code") int code, PrescriptionExerciseDTO prescriptionExerciseDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        prescriptionExerciseBean.update(code, prescriptionExerciseDTO.getDuracao());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    public Response consult(@PathParam("code") int code) {
        PrescriptionExercise prescription = prescriptionExerciseBean.findPrescriptionExercise(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @PUT
    @Path("/expire/{code}")
    public Response expirePrescription(@PathParam("code") int code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        prescriptionExerciseBean.expirePrescription(code);
        return Response.status(Response.Status.OK).build();
    }
}
