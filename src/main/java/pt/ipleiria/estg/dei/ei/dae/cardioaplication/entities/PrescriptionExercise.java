package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getPatientPrescriptionsExercise",
                query = "SELECT p FROM PrescriptionExercise p WHERE p.patientUser.username = :username"
        ),
        @NamedQuery(
                name = "getPatientPrescriptionsExerciseCode",
                query = "SELECT p FROM PrescriptionExercise p WHERE p.patientUser.username = :username and p.code = :code"
        )
})

public class PrescriptionExercise extends Prescription implements Serializable {
    @ManyToOne
    @JoinColumn(name = "program_code")
    private Program program;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;

    public PrescriptionExercise() {

    }

    public PrescriptionExercise(int code, int duracao, Date insertionDate, Program program, PatientUser patientUser) {
        super(code, duracao, insertionDate);
        this.patientUser = patientUser;
        this.program = program;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
