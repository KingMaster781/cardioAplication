package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ExerciseDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ProgramDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Prescription;
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

@Path("pescription")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescriptionService {
    @EJB
    PrescriptionBean prescriptionBean;

    private PrescriptionDTO toDTO(Prescription prescription) {
        String vigor;
        if (prescription.isVigor()) {
            vigor = "Está em vigor";
        } else {
            vigor = "Não está em vigor";
        }

        return new PrescriptionDTO(
                prescription.getCode(),
                prescription.getDuracao(),
                prescription.getInsertionDate(),
                vigor,
                prescription.getProgram().getCode(),
                prescription.getPatientUser().getUsername()
        );
    }

    private List<PrescriptionDTO> toDTOs(List<Prescription> prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{code}")
    public Response getPrescriptionDetails(@PathParam("code") int code) {
        Prescription prescription = prescriptionBean.findPrescription(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @POST
    @Path("/")
    public Response create(PrescriptionDTO prescriptionDTO) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        prescriptionBean.create(prescriptionDTO.getCode(),
                prescriptionDTO.getDuracao(),
                prescriptionDTO.getInsertionDate(),
                prescriptionDTO.getPatientUser_username(),
                prescriptionDTO.getProgramCode());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    public Response remove(@PathParam("code") int code) throws MyEntityNotFoundException {
        prescriptionBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    public Response update(@PathParam("code") int code, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        prescriptionBean.update(code, prescriptionDTO.getDuracao());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    public Response consult(@PathParam("code") int code) {
        Prescription prescription = prescriptionBean.findPrescription(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }
}
