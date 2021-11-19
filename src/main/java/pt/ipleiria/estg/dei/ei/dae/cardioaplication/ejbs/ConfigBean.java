package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    AdminBean adminBean;
    @EJB
    PatientUserBean patientUserBean;
    @EJB
    ProfHealthcareBean profHealthcareBean;

    @PostConstruct
    public void populateDB(){
        System.out.println("cardioAplication, Autoria: Rodrigo Domingues");
        adminBean.create("admin1", "123", "Joaquim", "admin1@my.cardio.pt");
        patientUserBean.create("xavier123", "123", "Xavier dos Santos", "xavier123@gmail.com");
        profHealthcareBean.create("federico.santos", "123", "Federico Santos", "federico.santos@my.cardio.pt");
    }
}
