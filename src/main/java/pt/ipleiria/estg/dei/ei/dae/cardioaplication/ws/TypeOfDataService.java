package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PatientUserDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ProfHealthcareDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.TypeOfDataDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.TypeOfDataBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Data;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.TypeOfData;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("typeofdata")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TypeOfDataService {
    @EJB
    private TypeOfDataBean typeOfDataBean;

    private TypeOfDataDTO toDTO(TypeOfData typeOfData){
        return new TypeOfDataDTO(
                typeOfData.getCode(),
                typeOfData.getDescType(),
                typeOfData.getValorMinimo(),
                typeOfData.getValorMaximo(),
                typeOfData.getUnidadeValorQuantitativo(),
                typeOfData.getListaValoresQualitativos()
        );
    }

    private List<TypeOfDataDTO> toDTOs(List<TypeOfData> typeOfDatas){
        return typeOfDatas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<TypeOfDataDTO> getAllPatientWS(){
        return toDTOs(typeOfDataBean.getAllTypesOfData());
    }

    @GET
    @Path("{code}")
    public Response consult(@PathParam("code") int code) {
        TypeOfData typeData = typeOfDataBean.findTypeOfDate(code);
        if (typeData!=null)
        {
            return Response.ok(toDTO(typeData)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Tipo de dados pedido não existe").build();
    }

    @POST
    @Path("/")
    public Response createNewStudent (TypeOfDataDTO typeOfDataDTO){
        typeOfDataBean.create(typeOfDataDTO.getCode(),
                typeOfDataDTO.getDescType(),
                typeOfDataDTO.getValorMinimo(),
                typeOfDataDTO.getValorMaximo(),
                typeOfDataDTO.getUnidadeValorQuantitativo(),
                typeOfDataDTO.getListaValoresQualitativos()
        );
        TypeOfData typeOfData = typeOfDataBean.findTypeOfDate(typeOfDataDTO.getCode());
        if(typeOfData == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(typeOfData))
                .build();
    }
}
