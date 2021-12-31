package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Medicine;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PrescriptionMedic;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class MedicineBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, String name, String desc, String warning) throws MyConstraintViolationException, MyEntityExistsException {
        if(findMedicine(code) != null)
        {
            throw new MyEntityExistsException("O medicamento com o código " + code + " já foi inserido");
        }
        try
        {
            Medicine medicine = new Medicine(code, name, desc, warning);
            eM.persist(medicine);
        }
        catch(ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public Medicine findMedicine(int code){
        return eM.find(Medicine.class, code);
    }

    public List<Medicine> getAllMedicine(){
        return eM.createNamedQuery("getAllMedicines").getResultList();
    }

    public void update(int code, String name, String desc, String warnings) throws MyEntityNotFoundException, MyConstraintViolationException {
        Medicine medicine = findMedicine(code);
        if(medicine == null)
        {
            throw new MyEntityNotFoundException("O medicamento com o código " + code + " não existe");
        }
        try
        {
            medicine.setName(name);
            medicine.setDescription(desc);
            medicine.setWarning(warnings);
        }
        catch(ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        Medicine medicine = findMedicine(code);
        if(medicine == null)
        {
            throw new MyEntityNotFoundException("O medicamento com o código " + code + " não existe");
        }
        for (PrescriptionMedic prescriptionMedic : medicine.getPrescriptionMedicList())
        {
            prescriptionMedic.removeMedicine(medicine);
        }
        eM.remove(medicine);
    }
}
