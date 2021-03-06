package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfHealthcareDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;
    private List<PatientUserDTO> patients;

    public ProfHealthcareDTO() {
        patients = new ArrayList<>();
    }

    public ProfHealthcareDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        patients = new ArrayList<>();
    }

    public List<PatientUserDTO> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientUserDTO> patients) {
        this.patients = patients;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
