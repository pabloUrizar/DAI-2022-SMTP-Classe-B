package ch.heigvd.dai.lab04.model.mail;

public class Message {
    private String from;
    private String to;
    private String subject;
    private String body;

    public Message(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}

