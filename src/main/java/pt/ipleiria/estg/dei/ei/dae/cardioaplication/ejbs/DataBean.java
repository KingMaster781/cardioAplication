package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Data;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.TypeOfData;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class DataBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, String insertionDate, String descriData, Double value, String patientUserName, int typeOfDataCode) throws MyEntityNotFoundException, MyEntityExistsException {
        if(findData(code)!= null){
            throw new MyEntityExistsException("Os dados com o código " + code + " já foram inseridos");
        }
        PatientUser patientUser = eM.find(PatientUser.class, patientUserName);
        if(patientUser == null){
            throw new MyEntityNotFoundException("O paciente com o username " + patientUserName + " não existe");
        }
        TypeOfData typeOfData = eM.find(TypeOfData.class, typeOfDataCode);
        if(typeOfData == null){
            throw new MyEntityNotFoundException("Os tipos de dados com o código " + typeOfDataCode + " não existem");
        }
        Data data = new Data(code, insertionDate, descriData, value, patientUser, typeOfData);
        eM.persist(data);
        patientUser.addData(data);
    }

    public List<Data> getPatientData(String patientUser_username){
        return eM.createNamedQuery("getPatientData", Data.class).setParameter("username", patientUser_username).getResultList();
    }

    public Data findData(int code)
    {
        return eM.find(Data.class, code);
    }

    public void update(int code, double value) throws MyEntityNotFoundException, MyConstraintViolationException {
        Data data = findData(code);
        if(data == null)
        {
            throw new MyEntityNotFoundException("Os dados com o código " + code + " não existem");
        }
        try
        {
            data.setValue(value);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        Data data = findData(code);
        if(data == null)
        {
            throw new MyEntityNotFoundException("Os dados com o código " + code + " não existem");
        }
        data.getPatientUser().removeData(data);
        eM.remove(data);
    }
}