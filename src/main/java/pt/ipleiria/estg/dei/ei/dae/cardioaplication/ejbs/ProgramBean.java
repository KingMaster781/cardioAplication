package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.*;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProgramBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, String nome, String descProgram) throws MyEntityExistsException, MyConstraintViolationException {
        if(findProgram(code) != null)
        {
            throw new MyEntityExistsException("A prescrição com o código " + code + " já foi inserida");
        }
        try
        {
            Program newProgram = new Program(code, nome, descProgram);
            eM.persist(newProgram);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public Program findProgram(int code)
    {
        return eM.find(Program.class, code);
    }

    public List<Program> getAllPrograms()
    {
        return (List<Program>) eM.createNamedQuery("getAllPrograms").getResultList();
    }

    public void update(int code, String nome, String descProgram) throws MyConstraintViolationException, MyEntityNotFoundException {
        Program program = findProgram(code);
        if(program == null)
        {
            throw new MyEntityNotFoundException("O programa com o código " + code + " não existe");
        }
        try
        {
            program.setName(nome);
            program.setDescProgram(descProgram);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        Program program = findProgram(code);
        if(program == null)
        {
            throw new MyEntityNotFoundException("O programa com o código " + code + " não existe");
        }
        for (Exercise exercise : program.getExercises()) {
            exercise.removeProgram(program);
        }
        for (PrescriptionExercise prescriptionExercise : program.getPrescriptions())
        {
            prescriptionExercise.getPatientUser().removePrescriptionExercises(prescriptionExercise);
            eM.remove(prescriptionExercise);
        }
        eM.remove(program);
    }

    public void enrollExercise(int codeProgram, int codeExercise) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Program program = findProgram(codeProgram);
        if(program == null)
        {
            throw new MyEntityNotFoundException("O programa com o código " + codeProgram + " não existe");
        }
        Exercise exercise = eM.find(Exercise.class, codeExercise);
        if(exercise == null)
        {
            throw new MyEntityNotFoundException("O exercicio com o código " + codeExercise + " não existe");
        }
        if(program.getExercises().contains(exercise))
        {
            throw new MyIllegalArgumentException("O programa com o código " + codeProgram + " já possui o exercicio " + codeExercise);
        }
        program.addExercise(exercise);
        exercise.addProgram(program);
    }

    public void unrollExercise(int codeProgram, int codeExercise) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Program program = findProgram(codeProgram);
        if(program == null)
        {
            throw new MyEntityNotFoundException("O programa com o código " + codeProgram + " não existe");
        }
        Exercise exercise = eM.find(Exercise.class, codeExercise);
        if(exercise == null)
        {
            throw new MyEntityNotFoundException("O exercicio com o código " + codeExercise + " não existe");
        }
        if(!program.getExercises().contains(exercise))
        {
            throw new MyIllegalArgumentException("O programa com o código " + codeProgram + " já não possui o exercicio " + codeExercise);
        }
        program.removeExercise(exercise);
        exercise.removeProgram(program);
    }
}
