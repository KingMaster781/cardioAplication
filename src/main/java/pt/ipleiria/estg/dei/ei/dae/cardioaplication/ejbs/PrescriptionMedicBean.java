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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Stateless
public class PrescriptionMedicBean {
    @PersistenceContext
    EntityManager eM;

    public void create(int code, int duracao, String insertiondataString, String patientUser_username) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if(findPrescriptionMedic(code) != null)
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
            PrescriptionMedic newPrescription = new PrescriptionMedic(code, duracao, insertiondata, patientUser);
            eM.persist(newPrescription);
            patientUser.addPrescriptionMedics(newPrescription);
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public PrescriptionMedic findPrescriptionMedic(int code)
    {
        return eM.find(PrescriptionMedic.class, code);
    }

    public List<PrescriptionMedic> getAllPatientMedicPrescriptions(String patientUser_username){
        return eM.createNamedQuery("getPatientPrescriptionsMedic", PrescriptionMedic.class).setParameter("username", patientUser_username).getResultList();
    }

    public List<PrescriptionMedic> getAllPatientMedicPrescriptionsCode(String patientUser_username, int code){
        return eM.createNamedQuery("getPatientPrescriptionsMedicCode", PrescriptionMedic.class).setParameter("username", patientUser_username).setParameter("code", code).getResultList();
    }

    public void update(int code, int duracao) throws MyEntityNotFoundException, MyConstraintViolationException {
        PrescriptionMedic prescription = findPrescriptionMedic(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        try
        {
            prescription.setDuracao(duracao);
            prescription.setInsertionDate(todayDate());
            prescription.setOldInsertionDate(todayDate());
        }
        catch (ConstraintViolationException e)
        {
            throw new MyConstraintViolationException(e);
        }
    }

    public void remove(int code) throws MyEntityNotFoundException {
        PrescriptionMedic prescription = findPrescriptionMedic(code);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A prescrição com o código " + code + " não existe");
        }
        prescription.getPatientUser().removePrescriptionMedics(prescription);
        for (Medicine medicine : prescription.getMedicines()) {
            medicine.removeMedicPrescription(prescription);
        }
        eM.remove(prescription);
    }

    public void expirePrescription(int code) throws MyIllegalArgumentException, MyEntityNotFoundException {
        PrescriptionMedic prescription = findPrescriptionMedic(code);
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

    public void enrollMedicine(int codePrescription, int codeMedicine) throws MyEntityNotFoundException, MyIllegalArgumentException {
        PrescriptionMedic prescription = findPrescriptionMedic(codePrescription);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A precrição com o código " + codePrescription + " não existe");
        }
        Medicine medicine = eM.find(Medicine.class, codeMedicine);
        if(medicine == null)
        {
            throw new MyEntityNotFoundException("O medicamento com o código " + codeMedicine + " não existe");
        }
        if(prescription.getMedicines().contains(medicine))
        {
            throw new MyIllegalArgumentException("A prescrição com o código " + codePrescription + " já possui o medicamento " + codeMedicine);
        }
        prescription.addMedicine(medicine);
        medicine.addMedicPrescription(prescription);
    }

    public void unrollMedicine(int codePrescription, int codeMedicine) throws MyEntityNotFoundException, MyIllegalArgumentException {
        PrescriptionMedic prescription = findPrescriptionMedic(codePrescription);
        if(prescription == null)
        {
            throw new MyEntityNotFoundException("A precrição com o código " + codePrescription + " não existe");
        }
        Medicine medicine = eM.find(Medicine.class, codeMedicine);
        if(medicine == null)
        {
            throw new MyEntityNotFoundException("O medicamento com o código " + codeMedicine + " não existe");
        }
        if(prescription.getMedicines().contains(medicine))
        {
            throw new MyIllegalArgumentException("A prescrição com o código " + codePrescription + " já possui o medicamento " + codeMedicine);
        }
        prescription.removeMedicine(medicine);
        medicine.removeMedicPrescription(prescription);
    }

    private Date todayDate()
    {
        LocalDate localTodayDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date todayDate = Date.from(localTodayDate.atStartOfDay(defaultZoneId).toInstant());;
        return todayDate;
    }
}
