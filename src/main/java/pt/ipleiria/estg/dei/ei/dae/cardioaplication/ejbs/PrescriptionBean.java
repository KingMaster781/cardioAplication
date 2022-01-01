package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.PatientUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Prescription;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    EntityManager eM;

    public List<Prescription> getAllPrescription(){
        return (List<Prescription>)eM.createNamedQuery("getAllPrescriptions").getResultList();
    }

    public void changeVigororDurantion()
    {
        List<Prescription> prescriptions = getAllPrescription();
        LocalDate localTodayDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();

         for(Prescription prescription : prescriptions)
        {
            int duration = prescription.getDuracao();
            Date insertionDate = prescription.getInsertionDate();
            Instant instant = insertionDate.toInstant();

            LocalDate insertionDateLocal = instant.atZone(defaultZoneId).toLocalDate();

            long daysdiff = Duration.between(localTodayDate.atStartOfDay(), insertionDateLocal.atStartOfDay()).toDays();

            if (daysdiff>=duration)
            {
                prescription.setVigor(false);
            }
            else
            {
                int durationToday = duration + (int) daysdiff;
                System.out.println(durationToday + "=" + duration + " - " + daysdiff);
                insertionDate=Date.from(localTodayDate.atStartOfDay(defaultZoneId).toInstant());
                prescription.setDuracao(durationToday);
                prescription.setInsertionDate(insertionDate);
            }
        }
    }
}
