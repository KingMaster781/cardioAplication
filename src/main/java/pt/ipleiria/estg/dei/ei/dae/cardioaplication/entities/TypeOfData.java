package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "typedata")
@NamedQueries(
        @NamedQuery(
                name = "getAllTypesOfData",
                query = "SELECT t FROM TypeOfData t ORDER BY t.descType"
        )
)
public class TypeOfData
{
    @Id
    private int code;
    @NotNull
    private String descType;
    @NotNull
    private String unity;

    public TypeOfData() {
    }

    public TypeOfData(int code, String descType, String unity) {
        this.code = code;
        this.descType = descType;
        this.unity = unity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescType() {
        return descType;
    }

    public void setDescType(String descType) {
        this.descType = descType;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }
}
