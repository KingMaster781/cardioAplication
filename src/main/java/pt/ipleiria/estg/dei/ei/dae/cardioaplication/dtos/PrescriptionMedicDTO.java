package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionMedicDTO {
    private int code;
    private int duracao;
    private String insertionDate;
    private String vigor; //alterar entre "Está em vigor" e "Não está em vigor" na camada de serviço
    private String patientUser_username;
    private List<MedicineDTO> medicineDTOList;

    public PrescriptionMedicDTO() {
        medicineDTOList = new ArrayList<>();
    }

    public PrescriptionMedicDTO(int code, int duracao, String insertionDate, String vigor, String patientUser_username) {
        this.code = code;
        this.duracao = duracao;
        this.insertionDate = insertionDate;
        this.vigor = vigor;
        this.patientUser_username = patientUser_username;
        medicineDTOList = new ArrayList<>();
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

    public String getPatientUser_username() {
        return patientUser_username;
    }

    public void setPatientUser_username(String patientUser_username) {
        this.patientUser_username = patientUser_username;
    }

    public List<MedicineDTO> getMedicineDTOList() {
        return medicineDTOList;
    }

    public void setMedicineDTOList(List<MedicineDTO> medicineDTOList) {
        this.medicineDTOList = medicineDTOList;
    }
}
