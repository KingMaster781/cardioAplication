package pt.ipleiria.estg.dei.ei.dae.cardioaplication.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
@NamedQueries({
        @NamedQuery(
                name = "GetAllMessageUser",
                query = "SELECT m FROM MessageUser m where m.userTo=:username order by m.date"
        )
})
public class MessageUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long code;
    @NotNull
    private String userTo;
    @NotNull
    private String userFrom;
    private String subject;
    @NotNull
    private String message;
    @NotNull
    private Date date;

    public MessageUser() {
    }

    public MessageUser(String userTo, String userFrom, String subject, String message, Date date) {
        this.userTo = userTo;
        this.userFrom = userFrom;
        this.subject = subject;
        this.message = message;
        this.date = date;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
