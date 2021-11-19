package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Data;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PatientUserBean {
    @PersistenceContext
    EntityManager eM;

    public void create(String username, String password, String name, String email)
    {
        PatientUser patientUser = new PatientUser(username, password, name, email);
        eM.persist(patientUser);
    }

    public List<PatientUser> getAllPatients(){
        return (List<PatientUser>) eM.createNamedQuery("getAllPatients").getResultList();
    }

    public PatientUser findPatient(String username)
    {
        return eM.find(PatientUser.class, username);
    }
}
