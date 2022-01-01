package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProfHealthcareBean {
    @PersistenceContext
    EntityManager eM;

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
        if(findProfHeathcare(username)!=null)
        {
            throw new MyEntityExistsException("O profissional de saude "+ username +" já existe");
        }
        try {
            ProfHealthcare profHealthcare = new ProfHealthcare(username, password, name, email);
            eM.persist(profHealthcare);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<ProfHealthcare> getAllHealthcares(){
        return (List<ProfHealthcare>) eM.createNamedQuery("getAllHealthcares").getResultList();
    }

    public ProfHealthcare findProfHeathcare(String username)
    {
        return eM.find(ProfHealthcare.class, username);
    }

    public PatientUser getProfHealthcarePatient(String usernameProf, String usernamePatient) throws MyEntityNotFoundException, MyIllegalArgumentException {
        ProfHealthcare profHealthcare = findProfHeathcare(usernameProf);
        if(profHealthcare == null)
        {
            throw new MyEntityNotFoundException("O profissional de saude "+ usernameProf +" não existe");
        }
        PatientUser patientUser = eM.find(PatientUser.class, usernamePatient);
        if(patientUser == null)
        {
            throw new MyEntityNotFoundException("O paciente "+ usernamePatient +" não existe");
        }
        if(!profHealthcare.getPatientUserList().contains(patientUser))
        {
            throw new MyIllegalArgumentException("O paciente "+ usernamePatient +" não o possui como profissional de saude");
        }
        return patientUser;
    }

    public void remove(String username) throws MyEntityNotFoundException {
        ProfHealthcare profHealthcare = findProfHeathcare(username);
        if(profHealthcare == null)
        {
            throw new MyEntityNotFoundException("O profissional de saude " + username + " não existe");
        }
        for (PatientUser patientUser: profHealthcare.getPatientUserList()) {
            patientUser.removeProfHealthcare(profHealthcare);
        }
        eM.remove(profHealthcare);
    }

    public void update(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
        ProfHealthcare profHealthcare = findProfHeathcare(username);
        if (profHealthcare == null)
        {
            throw new MyEntityExistsException("O profissional de saude "+ username +" já existe");
        }
        try {
            profHealthcare.setName(name);
            profHealthcare.setEmail(email);
            profHealthcare.setPassword(password);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void enrollPatient(String usernamePatient, String usernameProf) throws MyEntityNotFoundException, MyIllegalArgumentException {
        ProfHealthcare profHealthcare = findProfHeathcare(usernameProf);
        if(profHealthcare == null)
        {
            throw new MyEntityNotFoundException("O profissional de saude não é existente");
        }
        PatientUser patientUser = eM.find(PatientUser.class, usernamePatient);
        if(patientUser == null)
        {
            throw new MyEntityNotFoundException("O paciente não é existente");
        }
        if(profHealthcare.getPatientUserList().contains(patientUser))
        {
            throw new MyIllegalArgumentException("O profissional de saude que inseriu já contem este paciente");
        }
        profHealthcare.addPatient(patientUser);
        patientUser.addProfHealthcare(profHealthcare);
    }

    public void unrollPatient(String usernamePatient, String usernameProf) throws MyEntityNotFoundException, MyIllegalArgumentException {
        ProfHealthcare profHealthcare = findProfHeathcare(usernameProf);
        if(profHealthcare == null)
        {
            throw new MyEntityNotFoundException("O profissional de saude não é existente");
        }
        PatientUser patientUser = eM.find(PatientUser.class, usernamePatient);
        if(patientUser == null)
        {
            throw new MyEntityNotFoundException("O paciente não é existente");
        }
        if(!profHealthcare.getPatientUserList().contains(patientUser))
        {
            throw new MyIllegalArgumentException("Este profissional de saude já não contém este paciente");
        }
        profHealthcare.removePatient(patientUser);
        patientUser.removeProfHealthcare(profHealthcare);
    }
}
