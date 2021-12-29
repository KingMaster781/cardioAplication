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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
public class PrescriptionNutriBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, int duracao, String insertiondataString, String patientUser_username, String descNutri) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if(findPrescriptionNutri(code) != null)
        {
            throw new MyEntityExistsException("A prescrição com o código " + code + " já foi inserida");
        }
        PatientUser patientUser = eM.find(PatientUser.class, patientUser_username);
        if (patientUser == null)
        {
            throw new MyEntityNotFoundException("O paciente com o username " + patientUser_username + " não existe");
        }
        try
        {
            Date insertiondata = convertStringtoDate(insertiondataString);
            PrescriptionNutri newPrescription = new PrescriptionNutri(code, duracao, insertiondata, patientUser, descNutri);
            eM.persist(newPrescription);
            patientUser.addPrescriptionNutri(newPrescription);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public PrescriptionNutri findPrescriptionNutri(int code)
    {
        return eM.find(PrescriptionNutri.class, code);
    }

    public List<PrescriptionNutri> getAllPatientNutriPrescriptions(String patientUser_username){
        return eM.createNamedQuery("getPatientPrescriptionsNutri", PrescriptionNutri.class).setParameter("username", patientUser_username).getResultList();
    }

    public List<PrescriptionNutri> getAllPatientNutriPrescriptionsCode(String patientUser_username, int code){
        return eM.createNamedQuery("getPatientPrescriptionsNutriCode", PrescriptionNutri.class).setParameter("username", patientUser_username).setParameter("code", code).getResultList();
    }

    public void update(int code, int duracao, String descNutri) throws MyEntityNotFoundException, MyConstraintViolationException {
        PrescriptionNutri prescription = findPrescriptionNutri(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        try
        {
            prescription.setDuracao(duracao);
            prescription.setDescNutri(descNutri);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        PrescriptionNutri prescription = findPrescriptionNutri(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        prescription.getPatientUser().removePrescriptionNutri(prescription);
        eM.remove(prescription);
    }

    public void expirePrescription(int code) throws MyIllegalArgumentException, MyEntityNotFoundException {
        PrescriptionNutri prescription = findPrescriptionNutri(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        if(!prescription.isVigor())
        {
            throw new MyIllegalArgumentException("A prescrição com o código " + code + " já se encontra expirada");
        }
        prescription.setVigor(false);
    }

    private Date convertStringtoDate(String stringDate)
    {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try
        {
            if(!stringDate.equalsIgnoreCase("  /  /    "))
            {
                data = new Date(fmt.parse(stringDate).getTime());
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return data;
    }
}
