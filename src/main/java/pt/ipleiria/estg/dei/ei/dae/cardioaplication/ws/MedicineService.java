package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.MedicineDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.MedicineBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Medicine;
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
import java.util.List;
import java.util.stream.Collectors;

@Path("medicine")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MedicineService {
    @EJB
    private MedicineBean medicineBean;
    @Context
    private SecurityContext securityContext;

    private MedicineDTO toDTO (Medicine medicine)
    {
        return new MedicineDTO(
                medicine.getCode(),
                medicine.getName(),
                medicine.getDescription(),
                medicine.getWarning()
        );
    }

    private List<MedicineDTO> toDTOs(List<Medicine> medicines)
    {
        return medicines.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    @RolesAllowed({"Admin", "ProfHealthcare"})
    public List<MedicineDTO> getAllMedicines()
    {
        return toDTOs(medicineBean.getAllMedicine());
    }

    @GET
    @Path("{code}")
    public Response getMedicineDetails(@PathParam("code") int code)
    {
        if(!(securityContext.isUserInRole("Admin") || securityContext.isUserInRole("ProfHealthcare"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Medicine medicine = medicineBean.findMedicine(code);
        if(medicine!=null)
            return Response.ok(toDTO(medicine)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_MEDICINE").build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"Admin"})
    public Response create (MedicineDTO medicineDTO) throws MyConstraintViolationException, MyEntityExistsException {
        medicineBean.create(medicineDTO.getCode(),
                medicineDTO.getName(),
                medicineDTO.getDescription(),
                medicineDTO.getWarning());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"Admin"})
    public Response remove (@PathParam("code") int code) throws MyEntityNotFoundException {
        medicineBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    @RolesAllowed({"Admin"})
    public Response update (@PathParam("code") int code, MedicineDTO medicineDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        medicineBean.update(code, medicineDTO.getName(), medicineDTO.getDescription(), medicineDTO.getWarning());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{code}")
    public Response consult (@PathParam("code") int code){
        if(!(securityContext.isUserInRole("Admin") || securityContext.isUserInRole("ProfHealthcare"))) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Medicine medicine = medicineBean.findMedicine(code);
        if(medicine!=null)
            return Response.ok(toDTO(medicine)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_MEDICINE").build();
    }
}
