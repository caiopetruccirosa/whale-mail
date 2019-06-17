package mail;

import java.util.*;

public class Mail implements Cloneable {
	protected String from;
    protected String to;
    protected String subject;
    protected String[] cc;
    protected String[] bcc;
    protected String message;
    protected ArrayList<Attachment> attachments;

    public Mail(String from, String to, String[] cc, String[] bcc, String subject, String message) throws Exception {
    	if (from == null || from.trim() == "")
    		throw new Exception("Remetente nulo!");
    	
    	if (to == null || to.trim() == "")
    		throw new Exception("Destinatário nulo!");
    	
    	if (subject == null || subject.trim() == "")
    		throw new Exception("Assunto nulo!");
    	
    	if (message == null || message.trim() == "")
    		throw new Exception("Mensagem nula!");
    	
    	this.from = from;
    	this.to = to;
    	this.cc = new String[cc.length];
    	
    	for (int i = 0; i < cc.length; i++)
    		this.cc[i] = cc[i];
    	
    	this.bcc = new String[bcc.length];
    	for (int i = 0; i < bcc.length; i++)
    		this.bcc[i] = bcc[i];
    	
    	this.subject = subject;
    	this.message = message;
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
    
    public String getMessage() {
    	return this.message;
    }
    
    public Attachment[] getAttachments() {
    	return (Attachment[]) this.attachments.toArray();
    }
    
    public void addAttachment(Attachment a) throws Exception {
    	if (a == null)
    		throw new Exception("Attachment nulo!");
    	
    	this.attachments.add(a);
    }

    public int hashCode() {
    	int ret = 3;
    	
    	ret += this.from.hashCode()*7;
    	ret += this.to.hashCode()*7;
    	ret += this.subject.hashCode()*7;
    	ret += this.message.hashCode()*7;
    	
    	for (int i = 0; i < this.cc.length; i++)
    		ret += this.cc[i].hashCode()*7;
    	
    	for (int i = 0; i < this.bcc.length; i++)
    		ret += this.bcc[i].hashCode()*7;
    	
    	return ret;
    }

    public String toString() {
    	return "{" + this.from + ":" + this.to + ":" + this.subject + ":" + this.message + ":" + "}";
    }

    public boolean equals(Object obj) {
    	if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        Mail m = (Mail) obj;
        
        if (!this.from.equals(m.from))
        	return false;
        
        if (!this.to.equals(m.to))
        	return false;
        
        if (!this.subject.equals(m.subject))
        	return false;
        
        if (!this.message.equals(m.message))
        	return false;
        
        if (this.cc.length != m.cc.length)
        	return false;
        
        for (int i = 0; i < this.cc.length; i++)
        	if (!this.cc[i].equals(m.cc[i]))
        		return false;
        
        if (this.bcc.length != m.bcc.length)
        	return false;
        
        for (int i = 0; i < this.bcc.length; i++)
        	if (!this.bcc[i].equals(m.bcc[i]))
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
    	
    	this.from = m.from;
    	this.to = m.to;
    	
    	this.cc = new String[m.cc.length];
    	for (int i = 0; i < m.cc.length; i++)
    		this.cc[i] = m.cc[i];
    	
    	this.bcc = new String[m.bcc.length];
    	for (int i = 0; i < m.bcc.length; i++)
    		this.bcc[i] = m.bcc[i];
    	
    	this.subject = m.subject;
    	this.message = m.message;    	
    }
}