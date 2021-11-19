package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.AdminDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PatientUserDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PatientUserBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<PatientUserDTO> getAllPatientWS(){
        return toDTOs(patientUserBean.getAllPatients());
    }

    private PatientUserDTO toDTO(PatientUser patientUser){
        return new PatientUserDTO(
                patientUser.getUsername(),
                patientUser.getPassword(),
                patientUser.getName(),
                patientUser.getEmail()
        );
    }

    private List<PatientUserDTO> toDTOs(List<PatientUser> patientUsers){
        return patientUsers.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
