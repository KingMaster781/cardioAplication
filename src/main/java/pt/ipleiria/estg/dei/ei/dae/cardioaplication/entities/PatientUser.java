package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patientuser")
@NamedQueries({
        @NamedQuery(
                name = "getAllPatients",
                query = "SELECT p FROM PatientUser p ORDER BY p.name"
        )
})
public class PatientUser implements Serializable {
    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @ManyToOne
    @JoinColumn(name = "HeathcareProfessional_USERNAME")
    private ProfHealthcare profHealthcare;
    @OneToMany(mappedBy = "patientUser", cascade = CascadeType.REMOVE)
    private List<Data> dataList;
    @OneToMany(mappedBy = "patientUser", cascade = CascadeType.REMOVE)
    private List<Exam> examList;

    public PatientUser() {
        dataList = new ArrayList<>();
        examList = new ArrayList<>();
    }

    public PatientUser(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        dataList = new ArrayList<>();
        examList = new ArrayList<>();
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

    public ProfHealthcare getProfHealthcare() {
        return profHealthcare;
    }

    public void setProfHealthcare(ProfHealthcare profHealthcare) {
        this.profHealthcare = profHealthcare;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    public void addData(Data data)
    {
        if (data != null && !dataList.contains(data))
        {
            dataList.add(data);
        }
    }

    public void removeData(Data data)
    {
        if (data != null && !dataList.contains(data))
        {
            dataList.add(data);
        }
    }

    public void addExam(Exam exam)
    {
        if (exam != null && !examList.contains(exam))
        {
            examList.add(exam);
        }
    }

    public void removeExam(Exam exam)
    {
        if (exam != null && !examList.contains(exam))
        {
            examList.add(exam);
        }
    }
}
