package handlers;

import java.io.*;
import java.util.*;

import javax.activation.*;
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
        
    	Properties props = System.getProperties();
    	
    	props.put("mail.smtp.host", this.acc.getHost());
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.user", this.acc.getUser());
    	props.put("mail.smtp.password", this.acc.getPassword());
    	props.put("mail.smtp.port", this.acc.getPort());
    	props.put("mail.smtp.auth", "true");
    	
    	Authenticator auth = new Authenticator() {
	    	public PasswordAuthentication getPasswordAuthentication() {
		    	String username = acc.getUser();
		    	String password = acc.getPassword();
		    	
		    	if ((username != null) && (username.length() > 0) && (password != null) && (password.length () > 0))
		    		return new PasswordAuthentication(username, password);
		    	
	    		return null;
	    	}
    	};
        
        this.session = Session.getInstance(props, auth);
    }
    
    public void sendEmail(Mail mail) throws Exception {
    	try {
    		Message msg = new MimeMessage(this.session);
    		
            msg.setFrom(new InternetAddress(mail.getFrom()));
            
            String to = mail.getTo();
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            
            String[] cc = mail.getCC();
            if (cc != null)
	            for (int i = 0; i < cc.length; i++)
	            	msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc[i]));
            
            String[] bcc = mail.getBCC();
            if (bcc != null)
            	for (int i = 0; i < bcc.length; i++)
            		msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc[i]));

            msg.setSubject(mail.getSubject());
            
        	Multipart multipart = new MimeMultipart();
        	
        	MimeBodyPart body = new MimeBodyPart();
            body.setContent((String) mail.getMessage(), "text/html");
            multipart.addBodyPart(body);
            
            ArrayList<File> atts = mail.getAttachments();
            if (atts != null) {
            	for (int i = 0; i < atts.size(); i++) {
                	MimeBodyPart attachment = new MimeBodyPart();
                	
                	attachment.setDataHandler(new DataHandler(new FileDataSource(atts.get(i))));
                	attachment.setFileName(atts.get(i).getName());
                	
                	multipart.addBodyPart(attachment);
                }
            }
            
            msg.setContent(multipart);       
            
            //Transport transport = this.session.getTransport("smtp");
            //transport.connect(this.acc.getHost(), this.acc.getUser(), this.acc.getPassword());
            //transport.sendMessage(msg, msg.getAllRecipients());
            //transport.close();
            
            Transport.send(msg);
    	}
    	catch (Exception ex) {
    		throw new Exception("Não foi possível enviar o e-mail!");
    	}    	
    }
}