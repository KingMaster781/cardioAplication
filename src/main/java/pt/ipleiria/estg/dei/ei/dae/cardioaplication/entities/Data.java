package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    private String insertionDate;
    @NotNull
    private String descriData;
    @NotNull
    private Double value;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;
    @ManyToOne
    @JoinColumn(name = "data_typeOfData")
    private TypeOfData typeOfData;

    public Data() {
    }

    public Data(int code, String insertionDate, String descriData, Double value, PatientUser patientUser, TypeOfData typeOfData) {
        this.code = code;
        this.insertionDate = insertionDate;
        this.descriData = descriData;
        this.value = value;
        this.patientUser = patientUser;
        this.typeOfData = typeOfData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getDescriData() {
        return descriData;
    }

    public void setDescriData(String descriData) {
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

    public TypeOfData getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(TypeOfData typeOfData) {
        this.typeOfData = typeOfData;
    }
}