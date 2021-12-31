package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.AdminDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.AdminBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @GET
    @Path("/")
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

    @GET
    @Path("{username}")
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
    public Response create (AdminDTO adminDTO) throws MyConstraintViolationException, MyEntityExistsException {
        adminBean.create(adminDTO.getUsername(), adminDTO.getPassword(), adminDTO.getName(), adminDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{username}")
    public Response update (@PathParam("username") String username, AdminDTO adminDTO) throws MyConstraintViolationException, MyEntityNotFoundException {
        adminBean.update(username, adminDTO.getPassword(),adminDTO.getName(),adminDTO.getEmail());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{username}")
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

    @POST
    @Path("/{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email) throws MyEntityNotFoundException, MessagingException {
        Admin admin = adminBean.findAdmin(username);
        if (admin == null) {
            throw new MyEntityNotFoundException("Um admin com o username '" + username + "' não foi encontrado nos registos.");
        }
        emailBean.send(admin.getEmail(), email.getSubject(), email.getMessage());
        return Response.status(Response.Status.OK).entity("Email Enviado").build();
    }

}
