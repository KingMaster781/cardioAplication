package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ws;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.ExamDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos.PrescriptionExerciseDTO;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.ExamBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs.PrescriptionExerciseBean;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Exam;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionExercise;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("exams")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ExamService {
    @EJB
    ExamBean examBean;

    private ExamDTO toDTO(Exam exam) {
        return new ExamDTO(
                exam.getCode(),
                convertDatetoString(exam.getDate()),
                convertDatetoString(exam.getDateResult()),
                exam.getPatientUser().getUsername()
        );
    }

    private List<ExamDTO> toDTOs(List<Exam> exams) {
        return exams.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private String convertDatetoString(Date data)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(data);
    }

    private Date convertStringtoDate(String stringDate)
    {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try
        {
            if(!stringDate.equalsIgnoreCase("  /  /    "))
            {
                data = new Date(fmt.parse(stringDate).getTime());
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    @GET
    @Path("{code}")
    public Response getExam(@PathParam("code") int code) {
        Exam exam = examBean.findExam(code);
        if (exam != null)
            return Response.ok(toDTO(exam)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_EXAM").build();
    }

    @GET
    @Path("/user/{username}")
    public Response getUserExams(@PathParam("username") String  username) {
        List<Exam> exam = examBean.getAllPatientPrescriptionsExercises(username);
        if (exam != null)
            return Response.ok(toDTOs(exam)).build();
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_EXAM").build();
    }

    @POST
    @Path("/")
    public Response create(ExamDTO examDTO) throws MyConstraintViolationException, MyEntityExistsException, MyEntityNotFoundException {
        examBean.create(
                examDTO.getCode(),
                examDTO.getDate(),
                examDTO.getDateResult(),
                examDTO.getPatientUsername());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{code}")
    public Response remove(@PathParam("code") int code) throws MyEntityNotFoundException {
        examBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{code}")
    public Response update(@PathParam("code") int code, ExamDTO examDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        examBean.update(code, convertStringtoDate(examDTO.getDateResult()), examDTO.isDone());
        return Response.status(Response.Status.OK).build();
    }
}