package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getPatientPrescriptionsNutri",
                query = "SELECT p FROM PrescriptionNutri p WHERE p.patientUser.username = :username"
        ),
        @NamedQuery(
                name = "getPatientPrescriptionsNutriCode",
                query = "SELECT p FROM PrescriptionNutri p WHERE p.patientUser.username = :username and p.code = :code"
        )
})

public class PrescriptionNutri extends Prescription implements Serializable {
    private String descNutri;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;

    public PrescriptionNutri() {

    }

    public PrescriptionNutri(int code, int duracao, Date insertionDate, PatientUser patientUser, String descNutri) {
        super(code, duracao, insertionDate);
        this.patientUser = patientUser;
        this.descNutri = descNutri;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }

    public String getDescNutri() {
        return descNutri;
    }

    public void setDescNutri(String descNutri) {
        this.descNutri = descNutri;
    }
}
