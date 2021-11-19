package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ProfHealthcareDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.ProfHealthcareBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("profhealthcares")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProfHealthcareService {
    @EJB
    private ProfHealthcareBean profHealthcareBean;

    @GET
    @Path("/")
    public List<ProfHealthcareDTO> getAllProfHealthcareWS(){
        return toDTOs(profHealthcareBean.getAllHealthcares());
    }

    private ProfHealthcareDTO toDTO(ProfHealthcare profHealthcare){
        return new ProfHealthcareDTO(
                profHealthcare.getUsername(),
                profHealthcare.getPassword(),
                profHealthcare.getName(),
                profHealthcare.getEmail()
        );
    }

    private List<ProfHealthcareDTO> toDTOs(List<ProfHealthcare> profHealthcares){
        return profHealthcares.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
