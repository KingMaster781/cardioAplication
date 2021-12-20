package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import java.util.ArrayList;
import java.util.List;

public class PatientUserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private List<ProfHealthcareDTO> profHealthcareDTOList;

    public PatientUserDTO() {
        profHealthcareDTOList = new ArrayList<>();
    }

    public PatientUserDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        profHealthcareDTOList = new ArrayList<>();
    }

    public List<ProfHealthcareDTO> getProfHealthcareDTOList() {
        return profHealthcareDTOList;
    }

    public void setProfHealthcareDTOList(List<ProfHealthcareDTO> profHealthcareDTOList) {
        this.profHealthcareDTOList = profHealthcareDTOList;
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
