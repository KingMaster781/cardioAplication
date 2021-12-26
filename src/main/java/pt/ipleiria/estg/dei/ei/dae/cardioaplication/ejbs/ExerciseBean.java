package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Exercise;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Program;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ExerciseBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, String nome, String descExercise) throws MyEntityExistsException, MyConstraintViolationException {
        if(findExercise(code) != null)
        {
            throw new MyEntityExistsException("O exercicio com o código " + code + " já se encontra inserido");
        }
        try
        {
            Exercise exercise = new Exercise(code, nome, descExercise);
            eM.persist(exercise);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public Exercise findExercise(int code)
    {
        return eM.find(Exercise.class, code);
    }

    public List<Exercise> getAllExercise()
    {
        return (List<Exercise>) eM.createNamedQuery("getAllExercises").getResultList();
    }

    public void update(int code, String nome, String descExercise) throws MyEntityNotFoundException, MyConstraintViolationException {
        Exercise exercise = findExercise(code);
        if(exercise == null)
        {
            throw new MyEntityNotFoundException("O exercicio com o código " + code + " não existe");
        }
        try
        {
            exercise.setName(nome);
            exercise.setDescExercise(descExercise);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        Exercise exercise = findExercise(code);
        if(exercise == null)
        {
            throw new MyEntityNotFoundException("O exercicio com o código " + code + " não existe");
        }
        for(Program program : exercise.getPrograms())
        {
            program.removeExercise(exercise);
        }
        eM.remove(exercise);
    }
}
