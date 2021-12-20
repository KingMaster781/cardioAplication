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
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB(){
        try {
            System.out.println("cardioAplication, Autoria: Rodrigo Domingues");
            adminBean.create("admin1", "123", "Joaquim", "admin1@my.cardio.pt");
            patientUserBean.create("xavier123", "123", "Xavier dos Santos", "xavier123@gmail.com");
            patientUserBean.create("joaquim123", "123", "Joaquim Pedrosa", "joaquim123@gmail.com");
            patientUserBean.create("rafaela123", "123", "Rafeala Santos", "rafaela123@gmail.com");
            profHealthcareBean.create("federico.santos", "123", "Federico Santos", "federico.santos@my.cardio.pt");
            profHealthcareBean.enrollPatient("rafaela123", "federico.santos");
            profHealthcareBean.enrollPatient("joaquim123", "federico.santos");
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }
}
