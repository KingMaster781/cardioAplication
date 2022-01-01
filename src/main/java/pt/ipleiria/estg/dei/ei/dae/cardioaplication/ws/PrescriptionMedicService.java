package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.MedicineDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionMedicDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionMedicBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Medicine;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionMedic;
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

@Path("prescription-medics")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescriptionMedicService {
    @EJB
    PrescriptionMedicBean prescriptionMedicBean;

    private PrescriptionMedicDTO toDTO(PrescriptionMedic prescription) {
        String vigor;
        if (prescription.isVigor()) {
            vigor = "Está em vigor";
        } else {
            vigor = "Não está em vigor";
        }

        PrescriptionMedicDTO prescriptionMedicDTO = new PrescriptionMedicDTO(
                prescription.getCode(),
                prescription.getDuracao(),
                convertDatetoString(prescription.getInsertionDate()),
                vigor,
                prescription.getPatientUser().getUsername()
        );
        prescriptionMedicDTO.setMedicineDTOList(medicineDTOS(prescription.getMedicines()));
        return prescriptionMedicDTO;
    }

    private List<PrescriptionMedicDTO> toDTOs(List<PrescriptionMedic> prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private MedicineDTO medicineDTO(Medicine medicine) {
        return new MedicineDTO(
                medicine.getCode(),
                medicine.getName(),
                medicine.getDescription(),
                medicine.getWarning()
        );
    }

    private List<MedicineDTO> medicineDTOS(List<Medicine> medicines) {
        return medicines.stream().map(this::medicineDTO).collect(Collectors.toList());
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
        PrescriptionMedic prescription = prescriptionMedicBean.findPrescriptionMedic(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"ProfHealthcare"})
    public Response create(PrescriptionMedicDTO prescriptionMedicDTO) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        prescriptionMedicBean.create(prescriptionMedicDTO.getCode(),
                prescriptionMedicDTO.getDuracao(),
                prescriptionMedicDTO.getInsertionDate(),
                prescriptionMedicDTO.getPatientUser_username());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response remove(@PathParam("code") int code) throws MyEntityNotFoundException {
        prescriptionMedicBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response update(@PathParam("code") int code, PrescriptionMedicDTO prescriptionMedicDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        prescriptionMedicBean.update(code, prescriptionMedicDTO.getDuracao());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response consult(@PathParam("code") int code) {
        PrescriptionMedic prescription = prescriptionMedicBean.findPrescriptionMedic(code);
        if (prescription != null)
            return Response.ok(toDTO(prescription)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PRESCRIPTION").build();
    }

    @PUT
    @Path("/expire/{code}")
    @RolesAllowed({"ProfHealthcare"})
    public Response expirePrescription(@PathParam("code") int code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        prescriptionMedicBean.expirePrescription(code);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/{code}/medicine")
    @RolesAllowed({"ProfHealthcare"})
    public Response enrollMedicine (@PathParam("code") int code, MedicineDTO medicineDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        prescriptionMedicBean.enrollMedicine(code, medicineDTO.getCode());
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}/medicine")
    @RolesAllowed({"ProfHealthcare"})
    public Response unrollMedicine (@PathParam("code") int code, MedicineDTO medicineDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        prescriptionMedicBean.unrollMedicine(code, medicineDTO.getCode());
        return Response.status(Response.Status.OK).build();
    }
}
