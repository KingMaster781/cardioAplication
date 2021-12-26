package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    AdminBean adminBean;
    @EJB
    PatientUserBean patientUserBean;
    @EJB
    ProfHealthcareBean profHealthcareBean;
    @EJB
    ProgramBean programBean;
    @EJB
    PrescriptionBean prescriptionBean;
    @EJB
    ExerciseBean exerciseBean;
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB(){
        try {
            adminBean.create("admin1", "123", "Joaquim", "admin1@my.cardio.pt");
            patientUserBean.create("joaquim123", "123", "Joaquim Pedrosa", "joaquim123@gmail.com");
            patientUserBean.create("rafaela123", "123", "Rafeala Santos", "rafaela123@gmail.com");
            profHealthcareBean.create("federico.santos", "123", "Federico Santos", "federico.santos@my.cardio.pt");
            profHealthcareBean.enrollPatient("rafaela123", "federico.santos");
            profHealthcareBean.enrollPatient("joaquim123", "federico.santos");
            exerciseBean.create(1, "agachamentos", "realizar 30 agachamento em 30 min");
            exerciseBean.create(2, "flexões", "realizar 30 flexões em 30 min");
            programBean.create(1, "RPC1", "Para doentes que tenham apenas um acompanhamento ligeiro");
            programBean.enrollExercise(1,1);
            prescriptionBean.create(1, 30, "23/12/2021", "rafaela123", 1);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }
}
