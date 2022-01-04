package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionExercise;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Program;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Stateless
public class PrescriptionExerciseBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, int duracao, String insertiondataString, String patientUser_username, int codeProgram) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if(findPrescriptionExercise(code) != null)
        {
            throw new MyEntityExistsException("A prescrição com o código " + code + " já foi inserida");
        }
        PatientUser patientUser = eM.find(PatientUser.class, patientUser_username);
        if (patientUser == null)
        {
            throw new MyEntityNotFoundException("O paciente com o username " + patientUser_username + " não existe");
        }
        Program program = eM.find(Program.class, codeProgram);
        if (program == null)
        {
            throw new MyEntityNotFoundException("O programa de RPC com o código " + codeProgram + " não existe");
        }
        try
        {
            Date insertiondata = convertStringtoDate(insertiondataString);
            PrescriptionExercise newPrescription = new PrescriptionExercise(code, duracao, insertiondata, program, patientUser);
            eM.persist(newPrescription);
            patientUser.addPrescriptionExercises(newPrescription);
            program.addPrescription(newPrescription);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public PrescriptionExercise findPrescriptionExercise(int code)
    {
        return eM.find(PrescriptionExercise.class, code);
    }

    public List<PrescriptionExercise> getAllPatientPrescriptionsExercises(String patientUser_username){
        return eM.createNamedQuery("getPatientPrescriptionsExercise", PrescriptionExercise.class).setParameter("username", patientUser_username).getResultList();
    }

    public List<PrescriptionExercise> getAllPatientPrescriptionsExercisesCode(String patientUser_username, int code){
        return eM.createNamedQuery("getPatientPrescriptionsExerciseCode", PrescriptionExercise.class).setParameter("username", patientUser_username).setParameter("code", code).getResultList();
    }

    public void update(int code, int duracao) throws MyEntityNotFoundException, MyConstraintViolationException {
        PrescriptionExercise prescription = findPrescriptionExercise(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        try
        {
            prescription.setDuracao(duracao);
            prescription.setInsertionDate(todayDate());
            prescription.setOldInsertionDate(todayDate());
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        PrescriptionExercise prescription = findPrescriptionExercise(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        prescription.getPatientUser().removePrescriptionExercises(prescription);
        prescription.getProgram().removePrescription(prescription);
        eM.remove(prescription);
    }

    public void expirePrescription(int code) throws MyIllegalArgumentException, MyEntityNotFoundException {
        PrescriptionExercise prescription = findPrescriptionExercise(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        if(!prescription.isVigor())
        {
            throw new MyIllegalArgumentException("A prescrição com o código " + code + " já se encontra expirada");
        }
        prescription.setVigor(false);
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

    private Date todayDate()
    {
        LocalDate localTodayDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date todayDate = Date.from(localTodayDate.atStartOfDay(defaultZoneId).toInstant());;
        return todayDate;
    }

}
