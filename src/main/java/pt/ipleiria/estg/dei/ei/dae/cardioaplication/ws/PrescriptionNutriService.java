package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.MedicineDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionExerciseDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionMedicDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionNutriDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionMedicBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionNutriBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Medicine;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionMedic;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionNutri;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescription-nutris")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescriptionNutriService {
    @EJB
    PrescriptionNutriBean prescriptionNutriBean;

    private PrescriptionNutriDTO toDTO(PrescriptionNutri prescription) {
        String vigor;
        if (prescription.isVigor()) {
            vigor = "Está em vigor";
        } else {
            vigor = "Não está em vigor";
        }

        return new PrescriptionNutriDTO(
                prescription.getCode(),
                prescription.getDuracao(),
                convertDatetoString(prescription.getInsertionDate()),
                convertDatetoString(prescription.getOldInsertionDate()),
                vigor,
                prescription.getPatientUser().getUsername(),
                prescription.getDescNutri()
        );
    }

    private List<PrescriptionNutriDTO> toDTOs(List<PrescriptionNutri> prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String convertDatetoString(Date data)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(data);
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response getPrescriptionDetails(@PathParam("code") int code) {
        PrescriptionNutri prescription = prescriptionNutriBean.findPrescriptionNutri(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"ProfHealthcare"})
    public Response create(PrescriptionNutriDTO prescriptionNutriDTO) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        prescriptionNutriBean.create(prescriptionNutriDTO.getCode(),
                prescriptionNutriDTO.getDuracao(),
                prescriptionNutriDTO.getInsertionDate(),
                prescriptionNutriDTO.getPatientUser_username(),
                prescriptionNutriDTO.getDescNutri());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response remove(@PathParam("code") int code) throws MyEntityNotFoundException {
        prescriptionNutriBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response update(@PathParam("code") int code, PrescriptionNutriDTO prescriptionNutriDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        prescriptionNutriBean.update(code, prescriptionNutriDTO.getDuracao());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response consult(@PathParam("code") int code) {
        PrescriptionNutri prescription = prescriptionNutriBean.findPrescriptionNutri(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @PUT
    @Path("/expire/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response expirePrescription(@PathParam("code") int code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        prescriptionNutriBean.expirePrescription(code);
        return Response.status(Response.Status.OK).build();
    }
}
