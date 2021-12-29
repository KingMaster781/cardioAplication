package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Programs")
@NamedQueries({
        @NamedQuery(
                name = "getAllPrograms",
                query = "SELECT p FROM Program p ORDER BY p.name"
        )
})
public class Program implements Serializable {
    @Id
    private int code;
    @NotNull
    private String name;
    @NotNull
    private String descProgram;
    @ManyToMany
    @JoinTable(name = "PROGRAM_EXERCISES",
            joinColumns = @JoinColumn(name = "PROGRAM_CODE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "EXAM_CODE", referencedColumnName = "CODE"))
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE)
    private List<PrescriptionExercise> prescriptions;

    public Program() {
        exercises = new ArrayList<>();
        prescriptions = new ArrayList<>();
    }

    public Program(int code, String name, String descProgram) {
        this.code = code;
        this.name = name;
        this.descProgram = descProgram;
        exercises = new ArrayList<>();
        prescriptions = new ArrayList<>();
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<PrescriptionExercise> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionExercise> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addExercise(Exercise exercise)
    {
        if(exercise != null && !exercises.contains(exercise))
        {
            exercises.add(exercise);
        }
    }

    public void removeExercise(Exercise exercise)
    {
        if(exercise != null && exercises.contains(exercise))
        {
            exercises.remove(exercise);
        }
    }

    public void addPrescription(PrescriptionExercise prescription)
    {
        if(prescription != null && !prescriptions.contains(prescription))
        {
            prescriptions.add(prescription);
        }
    }

    public void removePrescription(PrescriptionExercise prescription)
    {
        if(prescription != null && prescriptions.contains(prescription))
        {
            prescriptions.remove(prescription);
        }
    }
}
