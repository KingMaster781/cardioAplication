package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PatientUserBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionExerciseBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionMedicBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionNutriBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.*;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("patientusers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PatientUserService {
    @EJB
    private PatientUserBean patientUserBean;
    @EJB
    private PrescriptionExerciseBean prescriptionExerciseBean;
    @EJB
    private PrescriptionMedicBean prescriptionMedicBean;
    @EJB
    private PrescriptionNutriBean prescriptionNutriBean;

    @GET
    @Path("/")
    //@RolesAllowed({"Admin, PatientUser"})
    public List<PatientUserDTO> getAllPatientWS(){
        return toDTOsnoProfHealthcare(patientUserBean.getAllPatients());
    }

    private PatientUserDTO toDTOnoProfHealthcare(PatientUser patientUser){
        PatientUserDTO patientUserDTO = new PatientUserDTO(
                patientUser.getUsername(),
                patientUser.getPassword(),
                patientUser.getName(),
                patientUser.getEmail()
        );
        patientUserDTO.setPrescriptionExerciseDTOList(prescriptionExerciseDTOS(patientUser.getPrescriptionExercises()));
        patientUserDTO.setPrescriptionMedicDTOList(prescriptionMedicDTOS(patientUser.getPrescriptionMedics()));
        patientUserDTO.setPrescriptionNutriDTOList(prescriptionNutriDTOS(patientUser.getPrescriptionNutris()));
        return patientUserDTO;
    }

    private List<PatientUserDTO> toDTOsnoProfHealthcare(List<PatientUser> patientUsers){
        return patientUsers.stream().map(this::toDTOnoProfHealthcare).collect(Collectors.toList());
    }

    private PatientUserDTO toDTO(PatientUser patientUser){
        PatientUserDTO patientUserDTO = new PatientUserDTO(
                patientUser.getUsername(),
                patientUser.getPassword(),
                patientUser.getName(),
                patientUser.getEmail()
        );
        patientUserDTO.setProfHealthcareDTOList(profHealthcaretoDTOs(patientUser.getProfHealthcare()));
        patientUserDTO.setPrescriptionExerciseDTOList(prescriptionExerciseDTOS(patientUser.getPrescriptionExercises()));
        patientUserDTO.setPrescriptionMedicDTOList(prescriptionMedicDTOS(patientUser.getPrescriptionMedics()));
        patientUserDTO.setPrescriptionNutriDTOList(prescriptionNutriDTOS(patientUser.getPrescriptionNutris()));
        return patientUserDTO;
    }

    private List<PatientUserDTO> toDTOs(List<PatientUser> patientUsers){
        return patientUsers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ProfHealthcareDTO profHealthcaretoDTO(ProfHealthcare profHealthcare){
        return new ProfHealthcareDTO(
                profHealthcare.getUsername(),
                profHealthcare.getPassword(),
                profHealthcare.getName(),
                profHealthcare.getEmail()
        );
    }

    private List<ProfHealthcareDTO> profHealthcaretoDTOs(List<ProfHealthcare> profHealthcares){
        return profHealthcares.stream().map(this::profHealthcaretoDTO).collect(Collectors.toList());
    }

    private PrescriptionExerciseDTO prescriptionExerciseDTO(PrescriptionExercise prescription) {
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

    private List<PrescriptionExerciseDTO> prescriptionExerciseDTOS(List<PrescriptionExercise> prescriptions) {
        return prescriptions.stream().map(this::prescriptionExerciseDTO).collect(Collectors.toList());
    }

    private PrescriptionMedicDTO prescriptionMedicineDTO(PrescriptionMedic prescription) {
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

    private List<PrescriptionMedicDTO> prescriptionMedicDTOS(List<PrescriptionMedic> prescriptions) {
        return prescriptions.stream().map(this::prescriptionMedicineDTO).collect(Collectors.toList());
    }

    private PrescriptionNutriDTO prescriptionNutriDTO(PrescriptionNutri prescription) {
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
                vigor,
                prescription.getPatientUser().getUsername(),
                prescription.getDescNutri()
        );
    }

    private List<PrescriptionNutriDTO> prescriptionNutriDTOS(List<PrescriptionNutri> prescriptions) {
        return prescriptions.stream().map(this::prescriptionNutriDTO).collect(Collectors.toList());
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
    @Path("{username}")
    public Response getPatientDetail(@PathParam("username") String username)
    {
        PatientUser patientUser = patientUserBean.findPatient(username);
        if (patientUser != null)
            return Response.ok(toDTO(patientUser)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PATIENT").build();
    }

    @GET
    @Path("{username}/profHealthcares")
    //@RolesAllowed({"Admin, PatientUser"})
    public Response getPatientProfHealthcares(@PathParam("username") String username) {
        PatientUser patientUser = patientUserBean.findPatient(username);
        if(patientUser!=null)
        {
            List<ProfHealthcareDTO> dtos = profHealthcaretoDTOs(patientUser.getProfHealthcare());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PATIENT").build();
    }

    @GET
    @Path("{username}/prescription-exercises")
    //@RolesAllowed({"Admin, PatientUser"})
    public Response getPatientPrescriptionExercises(@PathParam("username") String username) {
        PatientUser patientUser = patientUserBean.findPatient(username);
        if(patientUser!=null)
        {
            List<PrescriptionExerciseDTO> dtos = prescriptionExerciseDTOS(patientUser.getPrescriptionExercises());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PATIENT").build();
    }

    @GET
    @Path("{username}/prescription-medics")
    //@RolesAllowed({"Admin, PatientUser"})
    public Response getPatientPrescriptionMedics(@PathParam("username") String username) {
        PatientUser patientUser = patientUserBean.findPatient(username);
        if(patientUser!=null)
        {
            List<PrescriptionMedicDTO> dtos = prescriptionMedicDTOS(patientUser.getPrescriptionMedics());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PATIENT").build();
    }

    @GET
    @Path("{username}/prescription-nutris")
    //@RolesAllowed({"Admin, PatientUser"})
    public Response getPatientPrescriptionNutris(@PathParam("username") String username) {
        PatientUser patientUser = patientUserBean.findPatient(username);
        if(patientUser!=null)
        {
            List<PrescriptionNutriDTO> dtos = prescriptionNutriDTOS(patientUser.getPrescriptionNutris());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PATIENT").build();
    }

    @POST
    @Path("/")
    //@RolesAllowed({"Admin"})
    public Response create(PatientUserDTO patientUserDTO) throws MyConstraintViolationException, MyEntityExistsException {
        patientUserBean.create(patientUserDTO.getUsername(), patientUserDTO.getPassword(), patientUserDTO.getName(), patientUserDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }
    @PUT
    @Path("{username}")
   // @RolesAllowed({"Admin, PatientUser"})
    public Response update (@PathParam("username") String username, PatientUserDTO patientUserDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        patientUserBean.update(username, patientUserDTO.getPassword(), patientUserDTO.getName(), patientUserDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{username}")
    //@RolesAllowed({"Admin"})
    public Response remove (@PathParam("username") String username) throws MyEntityNotFoundException {
        patientUserBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }
    @GET
    @Path("/{username}")
    //@RolesAllowed({"Admin"})
    public Response consult (@PathParam("username") String username)
    {
        PatientUser patientUser = patientUserBean.findPatient(username);
        if(patientUser == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.OK).entity(toDTO(patientUser)).build();
    }

    @GET
    @Path("{username}/prescription-exercises/{code}")
    public Response consultPrescriptionsExercises(@PathParam("username") String username,@PathParam("code") int code) {
        List<PrescriptionExercise> prescriptionExercises = prescriptionExerciseBean.getAllPatientPrescriptionsExercisesCode(username, code);
        if (prescriptionExercises.size()>0)
        {
            return Response.ok(prescriptionExerciseDTOS(prescriptionExercises)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Não possui nenhuma prescrição com esse código").build();
    }

    @GET
    @Path("{username}/prescription-medics/{code}")
    public Response consultPrescriptionsMedic(@PathParam("username") String username,@PathParam("code") int code) {
        List<PrescriptionMedic> prescriptionMedics = prescriptionMedicBean.getAllPatientMedicPrescriptionsCode(username, code);
        if (prescriptionMedics.size()>0)
        {
            return Response.ok(prescriptionMedicDTOS(prescriptionMedics)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Não possui nenhuma prescrição com esse código").build();
    }

    @GET
    @Path("{username}/prescription-nutris/{code}")
    public Response consultPrescriptionsNutri(@PathParam("username") String username,@PathParam("code") int code) {
        List<PrescriptionNutri> prescriptionNutris = prescriptionNutriBean.getAllPatientNutriPrescriptionsCode(username, code);
        if (prescriptionNutris.size()>0)
        {
            return Response.ok(prescriptionNutriDTOS(prescriptionNutris)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Não possui nenhuma prescrição com esse código").build();
    }
}
