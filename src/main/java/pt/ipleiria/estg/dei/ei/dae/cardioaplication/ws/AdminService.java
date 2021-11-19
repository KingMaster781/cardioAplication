package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.AdminDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.AdminBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("admin")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AdminService {
    @EJB
    private AdminBean adminBean;

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

}
