package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Exercises")
@NamedQueries({
        @NamedQuery(
                name = "getAllExercises",
                query = "SELECT e FROM Exercise e ORDER BY e.name"
        )
})
public class Exercise {
    @Id
    private int code;
    @NotNull
    private String name;
    @NotNull
    private String descExercise;
    @ManyToMany(mappedBy = "exercises")
    private List<Program> programs;

    public Exercise() {
        programs = new ArrayList<>();
    }

    public Exercise(int code, String name, String descExercise) {
        this.code = code;
        this.name = name;
        this.descExercise = descExercise;
        programs = new ArrayList<>();
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

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public void addProgram(Program program)
    {
        if(program != null && !programs.contains(program))
        {
            programs.add(program);
        }
    }

    public void removeProgram(Program program)
    {
        if(program != null && programs.contains(program))
        {
            programs.remove(program);
        }
    }
}
