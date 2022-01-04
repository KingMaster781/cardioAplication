package pt.ipleiria.estg.dei.ei.dae.cardioaplication.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.MessageUser;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions.MyEntityNotFoundException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless(name = "EmailEJB")
public class EmailBean {
    @PersistenceContext
    EntityManager eM;

    @Resource(name = "java:/jboss/mail/fakeSMTP")
    private Session mailSession;
    private static final Logger logger = Logger.getLogger("EmailBean.logger");
    public void send(String to, String subject, String text) {
        Thread emailJob = new Thread(() -> {
            Message message = new MimeMessage(mailSession);
            Date timestamp = new Date();
            try {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to, false));
                message.setSubject(subject);
                message.setText(text);
                message.setSentDate(timestamp);
                Transport.send(message);
            } catch (MessagingException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
        emailJob.start();
    }

    public List<MessageUser> getAllMessageUser(String username){
        return (List<MessageUser>)eM.createNamedQuery("GetAllMessageUser").setParameter("username", username).getResultList();
    }

    public MessageUser findMessage(long code)
    {
        return eM.find(MessageUser.class, code);
    }

    public void ReceiveMessage(String userFrom, String userTo, String subject, String message)
    {
        Date date = todayDate();
        MessageUser newMessage = new MessageUser(userTo, userFrom, subject, message, date);
        eM.persist(newMessage);
    }

    public void removeMessage(long code) throws MyEntityNotFoundException {
        MessageUser messageUser = findMessage(code);
        if(messageUser == null)
        {
            throw new MyEntityNotFoundException("Não existe mensagem com esse código");
        }
        eM.remove(messageUser);
    }

    private Date todayDate()
    {
        LocalDate localTodayDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date todayDate = Date.from(localTodayDate.atStartOfDay(defaultZoneId).toInstant());;
        return todayDate;
    }
}
