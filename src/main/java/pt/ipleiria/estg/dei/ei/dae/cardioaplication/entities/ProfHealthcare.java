package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllHealthcares",
                query = "SELECT h FROM ProfHealthcare h ORDER BY h.name"
        )
})
public class ProfHealthcare extends User implements Serializable {
    @ManyToMany
    @JoinTable(name = "PROFHEALTHCARE_PATIENTS",
            joinColumns = @JoinColumn(name = "PROFHEALTHCARE_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "PATIENT_USERNAME", referencedColumnName = "USERNAME"))
    private List<PatientUser> patientUserList;

    public ProfHealthcare() {
        patientUserList = new ArrayList<>();
    }

    public ProfHealthcare(String username, String password, String nome, String email) {
        super(username, password, nome, email);
        patientUserList = new ArrayList<>();
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
