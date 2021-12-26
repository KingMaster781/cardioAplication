package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ProgramDTO {
    private int code;
    private String name;
    private String descProgram;
    private List<ExerciseDTO> exercises;

    public ProgramDTO() {
        exercises = new ArrayList<>();
    }

    public ProgramDTO(int code, String name, String descProgram) {
        this.code = code;
        this.name = name;
        this.descProgram = descProgram;
        exercises = new ArrayList<>();
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

    public String getDescProgram() {
        return descProgram;
    }

    public void setDescProgram(String descProgram) {
        this.descProgram = descProgram;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
