package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProfHealthcareBean {
    @PersistenceContext
    EntityManager eM;

    public void create(String username, String password, String name, String email)
    {
        ProfHealthcare profHealthcare = new ProfHealthcare(username, password, name, email);
        eM.persist(profHealthcare);
    }

    public List<ProfHealthcare> getAllHealthcares(){
        return (List<ProfHealthcare>) eM.createNamedQuery("getAllHealthcares").getResultList();
    }

    public ProfHealthcare findProfHeathcare(String username)
    {
        return eM.find(ProfHealthcare.class, username);
    }
}
