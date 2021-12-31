package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;


public class DataDTO{
    private int code;
    private String insertionDate;
    private String descriData;
    private Double value;
    private String patientUsername;
    private int typeOfDataCode;

    public DataDTO() {
    }

    public DataDTO(int code, String insertionDate, String descriData, Double value, String patientUsername, int typeOfDataCode) {
        this.code = code;
        this.insertionDate = insertionDate;
        this.descriData = descriData;
        this.value = value;
        this.patientUsername = patientUsername;
        this.typeOfDataCode = typeOfDataCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getDescriData() {
        return descriData;
    }

    public void setDescriData(String descriData) {
        this.descriData = descriData;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public int getTypeOfDataCode() {
        return typeOfDataCode;
    }

    public void setTypeOfDataCode(int typeOfDataCode) {
        this.typeOfDataCode = typeOfDataCode;
    }
}
