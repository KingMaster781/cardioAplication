package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class ExamDTO {
    private int code;
    private String date;
    private String dateResult;
    private boolean isDone;
    private String patientUsername;

    public ExamDTO() {
        isDone=false;
    }

    public ExamDTO(int code, String date, String dateResult, String patientUsername) {
        this.code = code;
        this.date = date;
        this.dateResult = dateResult;
        this.patientUsername = patientUsername;
        isDone=false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateResult() {
        return dateResult;
    }

    public void setDateResult(String dateResult) {
        this.dateResult = dateResult;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }
}
