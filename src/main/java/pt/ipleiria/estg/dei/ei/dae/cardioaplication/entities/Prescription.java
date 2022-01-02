package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Prescriptions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllPrescriptions",
                query = "SELECT p FROM Prescription p ORDER BY p.duracao" // JPQL
        )
})

public class Prescription {
    @Id
    private int code;
    @NotNull
    private int duracao;
    @NotNull
    private Date insertionDate;
    private Date oldInsertionDate;
    private boolean isVigor;

    public Prescription() {
        isVigor = true;
    }

    public Prescription(int code, int duracao, Date insertionDate) {
        this.code = code;
        this.duracao = duracao;
        this.insertionDate = insertionDate;
        oldInsertionDate=insertionDate;
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

    public Date getOldInsertionDate() {
        return oldInsertionDate;
    }

    public void setOldInsertionDate(Date oldInsertionDate) {
        this.oldInsertionDate = oldInsertionDate;
    }
}
