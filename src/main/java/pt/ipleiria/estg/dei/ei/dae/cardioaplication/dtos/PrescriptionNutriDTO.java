package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

public class PrescriptionNutriDTO {
    private int code;
    private int duracao;
    private String insertionDate;
    private String oldInsertionDate;
    private String vigor; //alterar entre "Está em vigor" e "Não está em vigor" na camada de serviço
    private String patientUser_username;
    private String descNutri;

    public PrescriptionNutriDTO() {

    }

    public PrescriptionNutriDTO(int code, int duracao, String insertionDate, String oldInsertionDate, String vigor, String patientUser_username, String descNutri) {
        this.code = code;
        this.duracao = duracao;
        this.insertionDate = insertionDate;
        this.oldInsertionDate = oldInsertionDate;
        this.vigor = vigor;
        this.patientUser_username = patientUser_username;
        this.descNutri = descNutri;
    }

    public String getOldInsertionDate() {
        return oldInsertionDate;
    }

    public void setOldInsertionDate(String oldInsertionDate) {
        this.oldInsertionDate = oldInsertionDate;
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

    public String getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getVigor() {
        return vigor;
    }

    public void setVigor(String vigor) {
        this.vigor = vigor;
    }

    public String getDescNutri() {
        return descNutri;
    }

    public void setDescNutri(String descNutri) {
        this.descNutri = descNutri;
    }

    public String getPatientUser_username() {
        return patientUser_username;
    }

    public void setPatientUser_username(String patientUser_username) {
        this.patientUser_username = patientUser_username;
    }
}
