package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Medicines")
@NamedQueries(
        @NamedQuery(
                name = "getAllMedicines",
                query = "SELECT m FROM Medicine m ORDER BY m.name"
        )
)
public class Medicine implements Serializable {
    @Id
    private int code;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private String warning;
    @ManyToMany
    @JoinTable(name = "MEDICINE_PRESCRIPTIONMEDIC",
            joinColumns = @JoinColumn(name = "MEDICINE_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "PRESCRIPTIONMEDIC_CODE", referencedColumnName = "CODE"))
    private List<PrescriptionMedic> prescriptionMedicList;

    public Medicine() {
        prescriptionMedicList = new ArrayList<>();
    }

    public Medicine(int code, String name, String description, String warning) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.warning = warning;
        prescriptionMedicList = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public List<PrescriptionMedic> getPrescriptionMedicList() {
        return prescriptionMedicList;
    }

    public void setPrescriptionMedicList(List<PrescriptionMedic> prescriptionMedicList) {
        this.prescriptionMedicList = prescriptionMedicList;
    }

    public void addMedicPrescription(PrescriptionMedic prescription)
    {
        if(prescription != null && !prescriptionMedicList.contains(prescription))
        {
            prescriptionMedicList.add(prescription);
        }
    }

    public void removeMedicPrescription(PrescriptionMedic prescription)
    {
        if(prescription != null && prescriptionMedicList.contains(prescription))
        {
            prescriptionMedicList.remove(prescription);
        }
    }
}
