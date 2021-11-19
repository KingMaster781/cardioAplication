package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AdminBean {
    @PersistenceContext
    EntityManager eM;

    public void create(String username, String password, String name, String email)
    {
        Admin admin = new Admin(username, password, name, email);
        eM.persist(admin);
    }

    public List<Admin> getAllAdmins(){
        return (List<Admin>) eM.createNamedQuery("getAllAdmins").getResultList();
    }

    public Admin findAdmin(String username)
    {
        return eM.find(Admin.class, username);
    }
}
