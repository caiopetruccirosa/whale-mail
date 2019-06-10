public class MailHandler {
    protected Account acc;

    public MailHandler() {}

    public send(String to, String cc, String bcc) {
        Message msg = new MimeMessage(this.session);

        msg.setFrom(new InternetAddress(from));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));

        msg.setSubject(subject);

        Multipart multipart = new MimeMultipart();

        BodyPart body = new MimeBodyPart();

        multipart.addBodyPart(body);

        body = new MimeBodyPart();

        DataSource source = new FileDataSource();
        body.setDataHandler()
        body.setFileName()
        
        multipart.addBodyPart(body);

        msg.setContent(multipart);

        Transport.send(msg);
    }
}