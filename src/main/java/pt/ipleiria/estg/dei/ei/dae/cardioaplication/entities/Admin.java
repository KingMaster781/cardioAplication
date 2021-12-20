package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdmins",
                query = "SELECT a FROM Admin a ORDER BY a.name"
        )
})
public class Admin extends User implements Serializable {

    public Admin() {
    }

    public Admin(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
