package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionMedic;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionNutri;

import java.util.ArrayList;
import java.util.List;

public class PatientUserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private List<ProfHealthcareDTO> profHealthcareDTOList;
    private List<PrescriptionExerciseDTO> prescriptionExerciseDTOList;
    private List<PrescriptionMedicDTO> prescriptionMedicDTOList;
    private List<PrescriptionNutriDTO> prescriptionNutriDTOList;

    public PatientUserDTO() {
        profHealthcareDTOList = new ArrayList<>();
        prescriptionExerciseDTOList = new ArrayList<>();
        prescriptionMedicDTOList = new ArrayList<>();
        prescriptionNutriDTOList = new ArrayList<>();
    }

    public PatientUserDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        profHealthcareDTOList = new ArrayList<>();
        prescriptionExerciseDTOList = new ArrayList<>();
        prescriptionMedicDTOList = new ArrayList<>();
        prescriptionNutriDTOList = new ArrayList<>();
    }

    public List<ProfHealthcareDTO> getProfHealthcareDTOList() {
        return profHealthcareDTOList;
    }

    public void setProfHealthcareDTOList(List<ProfHealthcareDTO> profHealthcareDTOList) {
        this.profHealthcareDTOList = profHealthcareDTOList;
    }

    public List<PrescriptionExerciseDTO> getPrescriptionExerciseDTOList() {
        return prescriptionExerciseDTOList;
    }

    public void setPrescriptionExerciseDTOList(List<PrescriptionExerciseDTO> prescriptionExerciseDTOList) {
        this.prescriptionExerciseDTOList = prescriptionExerciseDTOList;
    }

    public List<PrescriptionMedicDTO> getPrescriptionMedicDTOList() {
        return prescriptionMedicDTOList;
    }

    public void setPrescriptionMedicDTOList(List<PrescriptionMedicDTO> prescriptionMedicDTOList) {
        this.prescriptionMedicDTOList = prescriptionMedicDTOList;
    }

    public List<PrescriptionNutriDTO> getPrescriptionNutriDTOList() {
        return prescriptionNutriDTOList;
    }

    public void setPrescriptionNutriDTOList(List<PrescriptionNutriDTO> prescriptionNutriDTOList) {
        this.prescriptionNutriDTOList = prescriptionNutriDTOList;
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
