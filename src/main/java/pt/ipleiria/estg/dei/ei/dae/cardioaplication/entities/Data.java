package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "data")
@NamedQueries({
        @NamedQuery(
                name = "getPatientData",
                query = "SELECT d FROM Data d WHERE d.patientUser.username = :username"
        )
})
public class Data{
    @Id
    private int code;
    @NotNull
    private Date insertionDate;
    @NotNull
    private Date descriData;
    @NotNull
    private Double value;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;

    public Data() {
    }

    public Data(int code, Date insertionDate, Date descriData, Double value, PatientUser patientUser) {
        this.code = code;
        this.insertionDate = insertionDate;
        this.descriData = descriData;
        this.value = value;
        this.patientUser = patientUser;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Date getDescriData() {
        return descriData;
    }

    public void setDescriData(Date descriData) {
        this.descriData = descriData;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }
}
