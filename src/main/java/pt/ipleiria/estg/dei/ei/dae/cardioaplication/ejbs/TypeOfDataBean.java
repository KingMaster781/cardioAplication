package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.TypeOfData;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

@Stateless
public class TypeOfDataBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, String descType, int valorMinimo, int valorMaximo, String unidadeValorQuantitativo, Map<Integer, String> listaValoresQualitativos){
        TypeOfData typeOfData = new TypeOfData(code, descType, valorMinimo, valorMaximo, unidadeValorQuantitativo, listaValoresQualitativos);
        eM.persist(typeOfData);
    }

    public List<TypeOfData> getAllTypesOfData(){
        return (List<TypeOfData>) eM.createNamedQuery("getAllTypesOfData").getResultList();
    }

    public TypeOfData findTypeOfDate(int code)
    {
        return eM.find(TypeOfData.class, code);
    }

    public TypeOfData update(int code, String descType, int valorMinimo, int valorMaximo, String unidadeValorQuantitativo) throws MyEntityExistsException, MyConstraintViolationException {
        TypeOfData typeOfData = findTypeOfDate(code);
        if (typeOfData == null)
        {
            throw new MyEntityExistsException("O tipo de dados com o código "+ code +" não existe");
        }
        try {
            typeOfData.setDescType(descType);
            typeOfData.setValorMaximo(valorMaximo);
            typeOfData.setValorMinimo(valorMinimo);
            typeOfData.setUnidadeValorQuantitativo(unidadeValorQuantitativo);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
        return typeOfData;
    }

    public void remove(int code) throws MyEntityNotFoundException {
        TypeOfData typeOfData = findTypeOfDate(code);
        if(typeOfData == null)
        {
            throw new MyEntityNotFoundException("O tipo de dados com o código " + code + " não existe");
        }
        eM.remove(typeOfData);
    }
}