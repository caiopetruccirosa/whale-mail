package mail;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;


public class Mail implements Cloneable {
	protected int id;
	protected String from;
    protected String to;
    protected String subject;
    protected String[] cc;
    protected String[] bcc;
    protected Date date;
    protected Object message;
    protected ArrayList<File> attachments;

    public Mail(int id, String from, String to, String[] cc, String[] bcc, String subject, Object message, Date date, ArrayList<File> attachments) throws Exception {
    	if (from == null || from.trim() == "")
    		throw new Exception("Remetente nulo!");
    	
    	if (to == null || to.trim() == "")
    		throw new Exception("Destinatário nulo!");
    	
    	if (subject == null || subject.trim() == "")
    		throw new Exception("Assunto nulo!");
    	
    	if (message == null)
    		throw new Exception("Mensagem nula!");
    	
    	this.from = from;
    	
    	this.to = to;
    	
    	if (cc != null) {
			this.cc = new String[cc.length];
    		
    		for (int i = 0; i < cc.length; i++)
    			this.cc[i] = cc[i];
    	}
    	else
    		this.cc = null;
    	
    	if (bcc != null) {
			this.bcc = new String[bcc.length];
	    	
	    	for (int i = 0; i < bcc.length; i++)
	    		this.bcc[i] = bcc[i];
    	}
    	else
    		this.bcc = null;
    	
    	this.id = id;
    	this.subject = subject;
    	this.message = message;
    	this.date = date;
    	
    	this.attachments = attachments;
    }
    
    public int getId() {
    	return this.id;
    }
    
    public Date getDate() {
    	return this.date;
    }
    
    public String getFrom() {
    	return this.from;
    }
    
    public String getTo() {
    	return this.to;
    }
    
    public String[] getCC() {
    	return this.cc;
    }
    
    public String[] getBCC() {
    	return this.bcc;
    }
    
    public String getSubject() {
    	return this.subject;
    }
    
    public String getMessage() throws Exception {
    	String ret = null;
    	
    	if (this.message instanceof String)
    		ret = (String)this.message;
    	else
    		if (this.message instanceof MimeMultipart)
    			ret = this.getTextFromMimeMultipart((MimeMultipart)this.message);
    	
    	return ret;
    }
    
    public ArrayList<File> getAttachments() {
    	return (ArrayList<File>) this.attachments;
    }
    
    public void addAttachment(File a) throws Exception {
    	if (a == null)
    		throw new Exception("Attachment nulo!");
    	
    	this.attachments.add(a);
    }
    
    protected String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception
    {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++)
        {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain"))
            {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            }
            else if (bodyPart.isMimeType("text/html"))
            {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            }
            else if (bodyPart.getContent() instanceof MimeMultipart)
            {
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }

        //result = result.replace("<", "&lt;").replace(">", "&gt;");

        return result;
    }

    public int hashCode() {
    	int ret = 3;
    	
    	ret += new Integer(this.id).hashCode()*7;
    	ret += this.from.hashCode()*7;
    	ret += this.subject.hashCode()*7;
    	ret += this.message.hashCode()*7;
    	ret += this.to.hashCode()*7;
    	
    	if (this.cc != null)
	    	for (int i = 0; i < this.cc.length; i++)
	    		ret += this.cc[i].hashCode()*7;
    	
    	if (this.bcc != null)
	    	for (int i = 0; i < this.bcc.length; i++)
	    		ret += this.bcc[i].hashCode()*7;
    	
    	return ret;
    }
    
    public String toString() {
    	return "{" + this.id + ":" + this.from + ":" + this.to.toString() + ":" + this.subject + ":" + this.message + "}";
    }

    public boolean equals(Object obj) {
    	if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        Mail m = (Mail) obj;
        
        if (this.id != m.id)
        	return false;
        
        if (!this.from.equals(m.from))
        	return false;
        
        if (!this.subject.equals(m.subject))
        	return false;
        
        if (this.message != null && m.message != null)
        	if (!this.message.equals(m.message))
            	return false;
        else
        	return false;
        
        if (this.to != null && m.to != null) {
        	if (!this.to.equals(m.to))
            	return false;
        }
        
        if (this.cc != null && m.cc != null) {
        	if (this.cc.length != m.cc.length)
            	return false;
            
            for (int i = 0; i < this.cc.length; i++)
            	if (!this.cc[i].equals(m.cc[i]))
            		return false;
        }
        else
        	return false;
        
        if (this.cc != null && m.cc != null) {
	        if (this.bcc.length != m.bcc.length)
	        	return false;
	        
	        for (int i = 0; i < this.bcc.length; i++)
	        	if (!this.bcc[i].equals(m.bcc[i]))
	        		return false;
        }
        else
        	return false;
        
        return true;
    }

    public Object clone()
    {
        Mail ret = null;

        try
        {
            ret = new Mail(this);
        }
        catch (Exception ex) {}

        return ret;
    }

    public Mail(Mail m) throws Exception {
    	if (m == null)
    		throw new Exception("Modelo nulo!");
    	
    	this.id = m.id;
    	this.from = m.from;
    	
    	this.to = m.to;
    	
    	if (m.cc != null)
    		this.cc = null;
    	else {
    		this.cc = new String[m.cc.length];
        	for (int i = 0; i < m.cc.length; i++)
        		this.cc[i] = m.cc[i];
    	}
    	
    	if (m.bcc != null)
    		this.bcc = null;
    	else {
    		this.bcc = new String[m.bcc.length];
        	for (int i = 0; i < m.bcc.length; i++)
        		this.bcc[i] = m.bcc[i];
    	}
    	
    	
    	
    	this.subject = m.subject;
    	this.message = m.message;
    	
    	this.attachments = new ArrayList<>(m.attachments);
    	
    	this.date = (Date) m.date.clone();
    }
}