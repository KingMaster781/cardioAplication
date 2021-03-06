package pt.ipleiria.estg.dei.ei.dae.cardioaplication.dtos;

public class EmailDTO {
    private long code;
    private String userTo;
    private String userFrom;
    private String subject;
    private String message;

    public EmailDTO() {
    }

    public EmailDTO(long code, String userTo, String userFrom, String subject, String message) {
        this.code=code;
        this.userTo = userTo;
        this.userFrom =userFrom;
        this.subject = subject;
        this.message = message;
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
}
