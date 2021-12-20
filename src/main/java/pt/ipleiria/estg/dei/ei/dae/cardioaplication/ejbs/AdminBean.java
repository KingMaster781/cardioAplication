package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Admin;
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
public class AdminBean {
    @PersistenceContext
    EntityManager eM;

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {
        if(findAdmin(username)!=null)
        {
            throw new MyEntityExistsException("O admin " + username + " já existe");
        }
        try {
            Admin admin = new Admin(username, password, name, email);
            eM.persist(admin);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }

    }

    public List<Admin> getAllAdmins(){
        return (List<Admin>) eM.createNamedQuery("getAllAdmins").getResultList();
    }

    public Admin findAdmin(String username)
    {
        return eM.find(Admin.class, username);
    }

    public void remove(String username) throws MyEntityNotFoundException {
        Admin admin = findAdmin(username);
        if(admin==null)
        {
            throw new MyEntityNotFoundException("O admin " + username + " não existe");
        }
        eM.remove(admin);
    }

    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException, MyConstraintViolationException {
        Admin admin = findAdmin(username);
        if(admin==null)
        {
            throw new MyEntityNotFoundException("O admin " + username + " não existe");
        }
        try
        {
            admin.setEmail(email);
            admin.setName(name);
            admin.setPassword(password);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }
}
