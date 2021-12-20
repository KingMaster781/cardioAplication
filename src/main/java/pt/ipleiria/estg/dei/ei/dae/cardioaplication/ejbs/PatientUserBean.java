package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Data;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class PatientUserBean {
    @PersistenceContext
    EntityManager eM;

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
        if(findPatient(username)!=null)
        {
            throw new MyEntityExistsException("O paciente "+ username +" já existe");
        }
        try {
            PatientUser patientUser = new PatientUser(username, password, name, email);
            eM.persist(patientUser);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<PatientUser> getAllPatients(){
        return (List<PatientUser>) eM.createNamedQuery("getAllPatients").getResultList();
    }

    public PatientUser findPatient(String username)
    {
        return eM.find(PatientUser.class, username);
    }

    public void remove(String username) throws MyEntityNotFoundException {
        PatientUser patientUser = findPatient(username);
        if(patientUser==null)
        {
            throw new MyEntityNotFoundException("O paciente " + username + " não existe");
        }
        //for (Data data : patientUser.getDataList()) {
        //    data.setPatientUser(null);
        //} ---> Código experimental
        for (ProfHealthcare profHealthcare: patientUser.getProfHealthcare()) {
            profHealthcare.removePatient(patientUser);
        }
        eM.remove(patientUser);
    }

    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException {
        PatientUser patientUser = findPatient(username);
        if(patientUser==null)
        {
           throw new MyEntityNotFoundException("O paciente " + username + " não existe");
        }
        try {
            patientUser.setEmail(email);
            patientUser.setName(name);
            patientUser.setPassword(password);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }
}
