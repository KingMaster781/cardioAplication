package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {
    @PersistenceContext
    EntityManager em;
    public User authenticate(final String username, final String password) throws Exception {
        User user = em.find(User.class, username);
        if (user != null &&
                user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }
        throw new Exception("Falhou ao autenticar-se com o username ('" + username + "'): Username desconhecido ou password errada");
    }
}
