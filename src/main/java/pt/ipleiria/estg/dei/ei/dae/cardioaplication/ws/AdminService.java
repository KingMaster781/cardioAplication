package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.AdminDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.AdminBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.MessageUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("admin")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AdminService {
    @EJB
    private AdminBean adminBean;
    @EJB
    private EmailBean emailBean;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    @RolesAllowed({"Admin"})
    public List<AdminDTO> getAllAdminsWS(){
        return toDTOs(adminBean.getAllAdmins());
    }

    private AdminDTO toDTO(Admin admin){
        return new AdminDTO(
                admin.getUsername(),
                admin.getPassword(),
                admin.getName(),
                admin.getEmail()
        );
    }

    private List<AdminDTO> toDTOs(List<Admin> admins){
        return admins.stream().map(this::toDTO).collect(Collectors.toList());
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

    private List<EmailDTO> emailDTOS(List<MessageUser> messageUserList){
        return messageUserList.stream().map(this::emailtoDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Admin"})
    public Response getAdminDetail(@PathParam("username") String username)
    {
        Admin admin = adminBean.findAdmin(username);
        if(admin != null)
        {
            return Response.ok(toDTO(admin)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_ADMIN").build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Admin"})
    public Response create (AdminDTO adminDTO) throws MyConstraintViolationException, MyEntityExistsException {
        adminBean.create(adminDTO.getUsername(), adminDTO.getPassword(), adminDTO.getName(), adminDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{username}")
    @RolesAllowed({"Admin"})
    public Response update (@PathParam("username") String username, AdminDTO adminDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        adminBean.update(username, adminDTO.getPassword(),adminDTO.getName(),adminDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{username}")
    @RolesAllowed({"Admin"})
    public Response remove (@PathParam("username") String username) throws MyEntityNotFoundException {
        adminBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{username}")
    public Response consult (@PathParam("username") String username){
        Admin admin = adminBean.findAdmin(username);
        if(admin==null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.OK).entity(toDTO(admin)).build();
    }

}
