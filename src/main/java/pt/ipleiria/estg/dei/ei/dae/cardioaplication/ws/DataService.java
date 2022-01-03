package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.DataDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.DataBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Data;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("data")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DataService {
    @EJB
    private DataBean dataBean;
    @Context
    private SecurityContext securityContext;

    private DataDTO toDTO(Data data){
        return new DataDTO(
                data.getCode(),
                data.getInsertionDate(),
                data.getDescriData(),
                data.getValue(),
                data.getPatientUser().getUsername(),
                data.getTypeOfData().getCode()
        );
    }

    private List<DataDTO> toDTOs(List<Data> datas){
        return datas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/{username}/{type}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response create(DataDTO dataDTO,@PathParam("username") String username, @PathParam("type") int type) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        dataBean.create(dataDTO.getCode(),
                dataDTO.getInsertionDate(),
                dataDTO.getDescriData(),
                dataDTO.getValue(),
                username,
                type
        );
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response consult(@PathParam("code") int code) {
        Data data = dataBean.findData(code);
        if (data!=null)
        {
            return Response.ok(toDTO(data)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Data não existe").build();
    }

    @PUT
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response update(@PathParam("code") int code, DataDTO dataDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        dataBean.update(code, dataDTO.getDescriData(), dataDTO.getValue());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"ProfHealthcare", "PatientUser"})
    public Response remove(@PathParam("code") int code) throws MyEntityNotFoundException {
        dataBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/patient/{username}")
    public Response consult(@PathParam("username") String username) {
        List<Data> data = dataBean.getPatientData(username);
        Principal principal = securityContext.getUserPrincipal();
        if(!(securityContext.isUserInRole("ProfHealthcare") || securityContext.isUserInRole("PatientUser") && principal.getName().equals(username))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if (data.size()>0)
        {
            return Response.ok(toDTOs(data)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Não foram encontrados dados para esse paciente").build();
    }
}