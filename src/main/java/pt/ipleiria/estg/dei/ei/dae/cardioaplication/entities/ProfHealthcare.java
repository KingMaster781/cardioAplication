package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HeathcareProfessional")
@NamedQueries({
        @NamedQuery(
                name = "getAllHealthcares",
                query = "SELECT h FROM ProfHealthcare h ORDER BY h.name"
        )
})
public class ProfHealthcare implements Serializable {
    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @OneToMany(mappedBy = "profHealthcare", cascade = CascadeType.REMOVE)
    private List<PatientUser> patientUserList;

    public ProfHealthcare() {
        patientUserList = new ArrayList<>();
    }

    public ProfHealthcare(String username, String password, String nome, String email) {
        this.username = username;
        this.password = password;
        this.name = nome;
        this.email = email;
        patientUserList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PatientUser> getPatientUserList() {
        return patientUserList;
    }

    public void setPatientUserList(List<PatientUser> patientUserList) {
        this.patientUserList = patientUserList;
    }

    public void addPatient(PatientUser patientUser)
    {
        if (patientUser != null && !patientUserList.contains(patientUser))
        {
            patientUserList.add(patientUser);
        }
    }

    public void removePatient(PatientUser patientUser)
    {
        if (patientUser != null && patientUserList.contains(patientUser))
        {
            patientUserList.remove(patientUser);
        }
    }

}
