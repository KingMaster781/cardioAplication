package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Prescriptions")
@NamedQueries({
        @NamedQuery(
                name = "getPatientPrescriptions",
                query = "SELECT p FROM Prescription p WHERE p.patientUser.username = :username"
        ),
        @NamedQuery(
                name = "getPatientPrescriptionsCode",
                query = "SELECT p FROM Prescription p WHERE p.patientUser.username = :username and p.code = :code"
        )
})

public class Prescription {
    @Id
    private int code;
    @NotNull
    private int duracao;
    @NotNull
    private Date insertionDate;
    private boolean isVigor;
    @ManyToOne
    @JoinColumn(name = "program_code")
    private Program program;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;

    public Prescription() {
        isVigor = true;
    }

    public Prescription(int code, int duracao, Date insertionDate, Program program, PatientUser patientUser) {
        this.code = code;
        this.duracao = duracao;
        this.insertionDate = insertionDate;
        this.program = program;
        this.patientUser = patientUser;
        isVigor = true;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public boolean isVigor() {
        return isVigor;
    }

    public void setVigor(boolean vigor) {
        isVigor = vigor;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }
}
