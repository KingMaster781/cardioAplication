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
                name = "getAllPatients",
                query = "SELECT p FROM PatientUser p ORDER BY p.name"
        )
})
public class PatientUser extends User implements Serializable {
    @ManyToMany(mappedBy = "patientUserList")
    private List<ProfHealthcare> profHealthcares;
    @OneToMany(mappedBy = "patientUser", cascade = CascadeType.REMOVE)
    private List<Data> dataList;
    @OneToMany(mappedBy = "patientUser", cascade = CascadeType.REMOVE)
    private List<Exam> examList;
    @OneToMany(mappedBy = "patientUser", cascade = CascadeType.REMOVE)
    private List<Prescription> prescriptions;

    public PatientUser() {
        dataList = new ArrayList<>();
        examList = new ArrayList<>();
        profHealthcares = new ArrayList<>();
        prescriptions = new ArrayList<>();
    }

    public PatientUser(String username, String password, String name, String email) {
        super(username, password, name, email);
        dataList = new ArrayList<>();
        examList = new ArrayList<>();
        profHealthcares = new ArrayList<>();
        prescriptions = new ArrayList<>();
    }

    public List<ProfHealthcare> getProfHealthcare() {
        return profHealthcares;
    }

    public void setProfHealthcare(List<ProfHealthcare> profHealthcare) {
        this.profHealthcares = profHealthcare;
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

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
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

    public void addProfHealthcare(ProfHealthcare profHealthcare)
    {
        if (profHealthcare != null && !profHealthcares.contains(profHealthcare))
        {
            profHealthcares.add(profHealthcare);
        }
    }

    public void removeProfHealthcare(ProfHealthcare profHealthcare)
    {
        if (profHealthcare != null && profHealthcares.contains(profHealthcare))
        {
            profHealthcares.remove(profHealthcare);
        }
    }

    public void addPrescription(Prescription prescription)
    {
        if (prescription != null && !prescriptions.contains(prescription))
        {
            prescriptions.add(prescription);
        }
    }

    public void removePrescription(Prescription prescription)
    {
        if (prescription != null && prescriptions.contains(prescription))
        {
            prescriptions.remove(prescription);
        }
    }
}
