package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "exam")
@NamedQueries({
        @NamedQuery(
                name = "getAllPatientExam",
                query = "SELECT e FROM Exam e WHERE e.patientUser.username = :username"
        )
})

public class Exam {
    @Id
    private int code;
    @NotNull
    private Date date;
    private Date dateResult;
    private boolean isDone;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;

    public Exam() {
        isDone = false;
    }

    public Exam(int code, Date date, Date dateResult, PatientUser patientUser) {
        this.code = code;
        this.date = date;
        this.dateResult = dateResult;
        isDone=false;
        this.patientUser = patientUser;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateResult() {
        return dateResult;
    }

    public void setDateResult(Date dateResult) {
        this.dateResult = dateResult;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }
}
