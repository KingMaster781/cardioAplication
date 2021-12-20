package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.ProfHealthcare;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.TypeOfData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
