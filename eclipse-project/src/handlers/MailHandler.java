package handlers;

import java.io.*;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
            String[] to = mail.getTo();
            for(int i = 0; i<to.length ;i++)
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to[i]));
            
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
            
            ArrayList<Attachment> atts = mail.getAttachments();
            for (int i = 0; i < atts.size(); i++) {
            	MimeBodyPart attachment = new MimeBodyPart();
            	attachment.setDataHandler(atts.get(i).getDataHandler());
            			
            	
            	if (atts.get(i).getFilename() != null) {
            		attachment.setFileName(atts.get(i).getName());
            	} else if (atts.get(i).getId() != null) {
            		attachment.setHeader("Content-ID", atts.get(i).getId());
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