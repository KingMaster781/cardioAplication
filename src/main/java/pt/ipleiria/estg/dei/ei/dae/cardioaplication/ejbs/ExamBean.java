package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.*;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
public class ExamBean {
    @PersistenceContext
    EntityManager eM;

    public Exam findExam(int code)
    {
        return eM.find(Exam.class, code);
    }

    public void create(int code, String dateString, String dateResultString, String patientUser_username) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if(findExam(code) != null)
        {
            throw new MyEntityExistsException("O exame com o código " + code + " já se encontra inserido");
        }
        PatientUser patientUser = eM.find(PatientUser.class, patientUser_username);
        if (patientUser == null)
        {
            throw new MyEntityNotFoundException("O paciente com o username " + patientUser_username + " não existe");
        }
        try
        {
            Date date = convertStringtoDate(dateString);
            Date dateResult = null;
            if(dateResultString != null){
                dateResult = convertStringtoDate(dateResultString);
            }
            Exam exam = new Exam(code, date, dateResult, patientUser);
            eM.persist(exam);
            patientUser.addExam(exam);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Exam> getAllPatientExams(String patientUser_username){
        return eM.createNamedQuery("getAllPatientExam", Exam.class).setParameter("username", patientUser_username).getResultList();
    }

    public void update(int code, Date dateResult, boolean isDone) throws MyEntityNotFoundException, MyConstraintViolationException {
        Exam exam = findExam(code);
        if(exam == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        try
        {
            exam.setDateResult(dateResult);
            exam.setDone(isDone);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        Exam exam = findExam(code);
        if(exam == null)
        {
            throw new MyEntityNotFoundException("O Exame com o código " + code + " não existe");
        }
        exam.getPatientUser().removeExam(exam);
        eM.remove(exam);
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
}
