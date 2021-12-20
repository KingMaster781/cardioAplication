package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.AdminDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PatientUserDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ProfHealthcareDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PatientUserBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("patientusers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PatientUserService {
    @EJB
    private PatientUserBean patientUserBean;

    @GET
    @Path("/")
    //@RolesAllowed({"Admin, PatientUser"})
    public List<PatientUserDTO> getAllPatientWS(){
        return toDTOsnoProfHealthcare(patientUserBean.getAllPatients());
    }

    private PatientUserDTO toDTOnoProfHealthcare(PatientUser patientUser){
        return new PatientUserDTO(
                patientUser.getUsername(),
                patientUser.getPassword(),
                patientUser.getName(),
                patientUser.getEmail()
        );
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
}
