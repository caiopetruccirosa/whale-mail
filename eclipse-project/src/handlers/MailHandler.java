package handlers;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import bd.dbos.*;
import mail.*;

public class MailHandler {
    protected Account acc;
    protected Session session;

    public MailHandler(Account acc) throws Exception 
    {
    	if (acc == null)
    		throw new Exception("Conta nula!");
    	
    	this.acc = acc;
        
        Properties prop = new Properties();        
        prop.put("mail.imap.host", "imap.gmail.com");
        prop.put("mail.imap.port", "imap");
        prop.put("mail.imap.starttls.enable", "true");

        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        
        this.session = Session.getDefaultInstance(prop, null);
        this.session.setDebug(false);
    }

    public void sendEmail(Mail mail, String format) throws Exception {
    	try {
    		Message msg = new MimeMessage(this.session);
    		
            msg.setFrom(new InternetAddress(mail.getFrom()));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            
            String[] cc = mail.getCC();
            for (int i = 0; i < cc.length; i++)
            	msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc[i]));
            
            String[] bcc = mail.getBCC();
            for (int i = 0; i < bcc.length; i++)
            	msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(bcc[i]));

            msg.setSubject(mail.getSubject());

            Multipart multipart = new MimeMultipart();
            
            MimeBodyPart body = new MimeBodyPart();
            body.setContent(mail.getMessage(), format);
            multipart.addBodyPart(body);
            
            Attachment[] atts = mail.getAttachments();
            for (int i = 0; i < atts.length; i++) {
            	MimeBodyPart attachment = new MimeBodyPart();
            	attachment.setDataHandler(atts[i].getDataHandler());
            	
            	if (atts[i].getFilename() != null) {
            		attachment.setFileName(atts[i].getName());
            	} else if (atts[i].getId() != null) {
            		attachment.setHeader("Content-ID", atts[i].getId());
            	}
            	
            	multipart.addBodyPart(attachment);
            }
            
            msg.setContent(multipart);
            
            Transport transport = this.session.getTransport("smtp");
            transport.connect(this.acc.getHost(), this.acc.getUser(), this.acc.getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
    	}
    	catch (Exception ex) {
    		throw new Exception("Não foi possível enviar o e-mail!");
    	}    	
    }
}