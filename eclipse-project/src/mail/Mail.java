package mail;

import java.util.*;
import javax.mail.internet.*;
import java.io.*;

public class Mail implements Cloneable {
	protected int id;
	protected String from;
    protected String[] to;
    protected String subject;
    protected String[] cc;
    protected String[] bcc;
    protected Date date;
    protected Object message;
    protected ArrayList<Attachment> attachments;
    protected String folder;

    public Mail(int id, String from, String[] to, String[] cc, String[] bcc, String subject, Object message, Date date, ArrayList<Attachment> attachments, String folder) throws Exception {
    	if (from == null || from.trim() == "")
    		throw new Exception("Remetente nulo!");
    	
    	if (to.length < 0)
    		throw new Exception("Destinatário nulo!");
    	
    	if (subject == null || subject.trim() == "")
    		throw new Exception("Assunto nulo!");
    	
    	if (message == null)
    		throw new Exception("Mensagem nula!");
    	
    	if (folder == null)
    		throw new Exception("Pasta nula!");
    	
    	this.from = from;
    	
    	this.to = new String[to.length];
  
    	for(int i = 0; i<to.length;i++)
    		this.to[i] = to[i];
    	
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
    	
    	this.folder = folder;
    }
    
    public int getId() {
    	return this.id;
    }
    
    public String getFolder() {
    	return this.folder;
    }
    
    public Date getDate() {
    	return this.date;
    }
    
    public String getFrom() {
    	return this.from;
    }
    
    public String[] getTo() {
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
    
    public Object getMessage() {
    	return this.message;
    }
    
    public ArrayList<Attachment> getAttachments() {
    	return (ArrayList<Attachment>) this.attachments;
    }
    
    public void addAttachment(Attachment a) throws Exception {
    	if (a == null)
    		throw new Exception("Attachment nulo!");
    	
    	this.attachments.add(a);
    }

    public int hashCode() {
    	int ret = 3;
    	
    	ret += new Integer(this.id).hashCode()*7;
    	ret += this.from.hashCode()*7;
    	ret += this.subject.hashCode()*7;
    	ret += this.message.hashCode()*7;
    	
    	for(int i=0; i<this.to.length; i++)
    		ret += this.to[i].hashCode()*7;
    	
    	if (this.cc != null)
	    	for (int i = 0; i < this.cc.length; i++)
	    		ret += this.cc[i].hashCode()*7;
    	
    	if (this.bcc != null)
	    	for (int i = 0; i < this.bcc.length; i++)
	    		ret += this.bcc[i].hashCode()*7;
    	
    	ret += this.folder.hashCode()*7;
    	
    	return ret;
    }

    public String toString() {
    	return "{" + this.id + ":" + this.from + ":" + this.to.toString() + ":" + this.subject + ":" + this.message + ":" + this.folder + "}";
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
        	if (this.to.length != m.to.length)
            	return false;
            
            for (int i = 0; i < this.to.length; i++)
            	if (!this.to[i].equals(m.to[i]))
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
        
        if (this.folder != null && m.folder != null) {
        	if (!this.folder.equals(m.folder))
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
    	
    	this.to = new String[m.to.length];
    	for (int i=0;i<m.to.length;i++) {
    		this.to[i] = m.to[i];
    	}
    	
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
    	
    	this.folder = m.folder;
    }
}