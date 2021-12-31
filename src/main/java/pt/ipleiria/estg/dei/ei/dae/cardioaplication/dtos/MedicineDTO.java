package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

public class MedicineDTO {

    private int code;
    private String name;
    private String description;
    private String warning;

    public MedicineDTO() {

    }

    public MedicineDTO(int code, String name, String description, String warning) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.warning = warning;
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

    public void setWarning(String advertencias) {
        this.warning = advertencias;
    }
}
