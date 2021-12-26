package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

public class ExerciseDTO {
    private int code;
    private String name;
    private String descExercise;

    public ExerciseDTO() {
    }

    public ExerciseDTO(int code, String name, String descExercise) {
        this.code = code;
        this.name = name;
        this.descExercise = descExercise;
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

    public String getDescExercise() {
        return descExercise;
    }

    public void setDescExercise(String descExercise) {
        this.descExercise = descExercise;
    }
}
