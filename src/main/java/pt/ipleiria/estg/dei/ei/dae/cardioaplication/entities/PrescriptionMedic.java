package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getPatientPrescriptionsMedic",
                query = "SELECT p FROM PrescriptionMedic p WHERE p.patientUser.username = :username"
        ),
        @NamedQuery(
                name = "getPatientPrescriptionsMedicCode",
                query = "SELECT p FROM PrescriptionMedic p WHERE p.patientUser.username = :username and p.code = :code"
        )
})

public class PrescriptionMedic extends Prescription implements Serializable {

    @ManyToMany(mappedBy = "prescriptionMedicList")
    private List<Medicine> medicines;
    @ManyToOne
    @JoinColumn(name = "patientuser_username")
    private PatientUser patientUser;

    public PrescriptionMedic() {
        medicines = new ArrayList<>();
    }

    public PrescriptionMedic(int code, int duracao, Date insertionDate, PatientUser patientUser) {
        super(code, duracao, insertionDate);
        this.patientUser = patientUser;
        medicines = new ArrayList<>();
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public void addMedicine(Medicine medicine)
    {
        if(medicine != null && !medicines.contains(medicine))
        {
            medicines.add(medicine);
        }
    }

    public void removeMedicine(Medicine medicine)
    {
        if(medicine != null && medicines.contains(medicine))
        {
            medicines.remove(medicine);
        }
    }
}
