package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PatientUserDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ProfHealthcareDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.ProfHealthcareBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.MessageUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("profhealthcares")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProfHealthcareService {
    @EJB
    private ProfHealthcareBean profHealthcareBean;
    @EJB
    private EmailBean emailBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<ProfHealthcareDTO> getAllProfHealthcareWS(){
        return toDTOsnoPatient(profHealthcareBean.getAllHealthcares());
    }

    private ProfHealthcareDTO toDTOnoPatient(ProfHealthcare profHealthcare){
        return new ProfHealthcareDTO(
                profHealthcare.getUsername(),
                profHealthcare.getPassword(),
                profHealthcare.getName(),
                profHealthcare.getEmail()
        );
    }

    private List<ProfHealthcareDTO> toDTOsnoPatient(List<ProfHealthcare> profHealthcares){
        return profHealthcares.stream().map(this::toDTOnoPatient).collect(Collectors.toList());
    }

    private ProfHealthcareDTO toDTO(ProfHealthcare profHealthcare){
        ProfHealthcareDTO profHealthcareDTO = new ProfHealthcareDTO(
                profHealthcare.getUsername(),
                profHealthcare.getPassword(),
                profHealthcare.getName(),
                profHealthcare.getEmail()
        );
        profHealthcareDTO.setPatients(patientsToDTOs(profHealthcare.getPatientUserList()));
        return profHealthcareDTO;
    }

    private List<ProfHealthcareDTO> toDTOs(List<ProfHealthcare> profHealthcares){
        return profHealthcares.stream().map(this::toDTOnoPatient).collect(Collectors.toList());
    }

    private PatientUserDTO patientsToDTO(PatientUser patientUser)
    {
        return new PatientUserDTO(
                patientUser.getUsername(),
                null,
                patientUser.getName(),
                patientUser.getEmail()
        );
    }

    private List<PatientUserDTO> patientsToDTOs(List<PatientUser> patientUsers)
    {
        return patientUsers.stream().map(this::patientsToDTO).collect(Collectors.toList());
    }

    private EmailDTO emailtoDTO(MessageUser messageUser){
        return new EmailDTO(
                messageUser.getCode(),
                messageUser.getUserTo(),
                messageUser.getUserFrom(),
                messageUser.getSubject(),
                messageUser.getMessage()
        );
    }

    private List<EmailDTO> emailtoDTOS(List<MessageUser> messageUserList){
        return messageUserList.stream().map(this::emailtoDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}")
    public Response getProfHealthcareDetail(@PathParam("username") String username)
    {
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("Admin") || securityContext.isUserInRole("PatientUser") || securityContext.isUserInRole("ProfHealthcare") && principal.getName().equals(username))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(profHealthcare != null)
            return Response.ok(toDTO(profHealthcare)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PROFHEALTHCARE").build();
    }

    @GET
    @Path("{username}/patients")
    @RolesAllowed({"Admin", "ProfHealthcare"})
    public Response getProfHealthPatients(@PathParam("username") String username) {
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        if(profHealthcare != null)
        {
            List<PatientUserDTO> dtos = patientsToDTOs(profHealthcare.getPatientUserList());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PROFESSIONAL_HEALTHCARE")
                .build();
    }

    @GET
    @Path("{usernameProf}/patient/{usernamePatient}")
    @RolesAllowed({"ProfHealthcare"})
    public Response getProfHealthPatient(@PathParam("usernameProf") String usernameProf, @PathParam("usernamePatient") String usernamePatient) throws MyEntityNotFoundException, MyIllegalArgumentException {
        PatientUser patientUser = profHealthcareBean.getProfHealthcarePatient(usernameProf, usernamePatient);
        return Response.ok(patientsToDTO(patientUser)).build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Admin"})
    public Response create(ProfHealthcareDTO profHealthcareDTO) throws MyConstraintViolationException, MyEntityExistsException {
        profHealthcareBean.create(profHealthcareDTO.getUsername(), profHealthcareDTO.getPassword(), profHealthcareDTO.getName(), profHealthcareDTO.getEmail());
        return  Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{username}")
    @RolesAllowed({"Admin"})
    public Response update (@PathParam("username") String username, ProfHealthcareDTO profHealthcareDTO) throws MyConstraintViolationException, MyEntityExistsException {
        profHealthcareBean.update(username, profHealthcareDTO.getPassword(), profHealthcareDTO.getName(), profHealthcareDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{username}")
    @RolesAllowed({"Admin"})
    public Response remove (@PathParam("username") String username) throws MyEntityNotFoundException {
        profHealthcareBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{username}")
    @RolesAllowed({"Admin"})
    public Response consult (@PathParam("username") String username)
    {
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        if (profHealthcare == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.OK).entity(toDTO(profHealthcare)).build();
    }

    @POST
    @Path("/{username}/enroll-patient")
    @RolesAllowed({"Admin", "ProfHealthcare"})
    public Response enrollPatient (@PathParam("username") String username, PatientUserDTO patientUserDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        profHealthcareBean.enrollPatient(patientUserDTO.getUsername(), username);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{username}/unroll-patient/")
    @RolesAllowed({"Admin", "ProfHealthcare"})
    public Response unrollPatient (@PathParam("username") String username, PatientUserDTO patientUserDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        profHealthcareBean.unrollPatient(patientUserDTO.getUsername(), username);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email) throws MyEntityNotFoundException, MessagingException {
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        if (profHealthcare == null) {
            throw new MyEntityNotFoundException("Um profissional de saude com o username '" + username + "' não foi encontrado nos registos.");
        }
        emailBean.send(profHealthcare.getEmail(), email.getSubject(), email.getMessage());
        emailBean.ReceiveMessage(email.getUserFrom(), email.getUserTo(), email.getSubject(), email.getMessage());
        return Response.status(Response.Status.OK).entity("Email Enviado").build();
    }

    @GET
    @Path("/{username}/email/receive")
    public Response getEmails(@PathParam("username") String username) throws MyEntityNotFoundException{
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        if (profHealthcare != null) {
            List<EmailDTO> dtos = emailtoDTOS(emailBean.getAllMessageUser(username));
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PROFESSIONAL_HEALTHCARE")
                .build();
    }

    @GET
    @Path("/{username}/email/receive/{code}")
    public Response getEmail(@PathParam("username") String username, @PathParam("code") long code) throws MyEntityNotFoundException, MessagingException {
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        if (profHealthcare == null) {
            throw new MyEntityNotFoundException("Um profissional de saude com o username '" + username + "' não foi encontrado nos registos.");
        }
        EmailDTO dto = emailtoDTO(emailBean.findMessage(code));
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{username}/email/delete/{code}")
    public Response deleteEmails(@PathParam("username") String username, @PathParam("code") long code) throws MyEntityNotFoundException {
        ProfHealthcare profHealthcare = profHealthcareBean.findProfHeathcare(username);
        if (profHealthcare == null) {
            throw new MyEntityNotFoundException("Um profissional de saude com o username '" + username + "' não foi encontrado nos registos.");
        }
        emailBean.removeMessage(code);
        return Response.status(Response.Status.OK).entity("Email Removido").build();
    }}
