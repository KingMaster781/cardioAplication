package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

public class PrescriptionDTO {
    private int code;
    private int duracao;
    private String insertionDate;
    private String vigor; //alterar entre "Está em vigor" e "Não está em vigor" na camada de serviço
    private int programCode;
    private String patientUser_username;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(int code, int duracao, String insertionDate, String vigor, int programCode, String patientUser_username) {
        this.code = code;
        this.duracao = duracao;
        this.insertionDate = insertionDate;
        this.vigor = vigor;
        this.programCode = programCode;
        this.patientUser_username = patientUser_username;
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

    public int getProgramCode() {
        return programCode;
    }

    public void setProgramCode(int programCode) {
        this.programCode = programCode;
    }

    public String getPatientUser_username() {
        return patientUser_username;
    }

    public void setPatientUser_username(String patientUser_username) {
        this.patientUser_username = patientUser_username;
    }
}