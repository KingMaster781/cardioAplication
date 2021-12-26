package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Program;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, int duracao, String insertiondata, String patientUser_username, int codeProgram) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if(findPrescription(code) != null)
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
            Prescription newPrescription = new Prescription(code, duracao, insertiondata, program, patientUser);
            eM.persist(newPrescription);
            patientUser.addPrescription(newPrescription);
            program.addPrescription(newPrescription);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public Prescription findPrescription(int code)
    {
        return eM.find(Prescription.class, code);
    }

    public List<Prescription> getAllPatientPrescriptions(String patientUser_username){
        return eM.createNamedQuery("getPatientPrescriptions", Prescription.class).setParameter("username", patientUser_username).getResultList();
    }

    public List<Prescription> getAllPatientPrescriptionsCode(String patientUser_username, int code){
        return eM.createNamedQuery("getPatientPrescriptionsCode", Prescription.class).setParameter("username", patientUser_username).setParameter("code", code).getResultList();
    }

    public void update(int code, int duracao) throws MyEntityNotFoundException, MyConstraintViolationException {
        Prescription prescription = findPrescription(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        try
        {
            prescription.setDuracao(duracao);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        Prescription prescription = findPrescription(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        prescription.getPatientUser().removePrescription(prescription);
        prescription.getProgram().removePrescription(prescription);
        eM.remove(prescription);
    }

}
