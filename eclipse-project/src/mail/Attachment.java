package mail;

import java.io.*;
import javax.activation.*;

public class Attachment implements Cloneable {
	protected FileDataSource file;
	protected String filename;
	protected String id;
	
	public Attachment(FileDataSource file, String filename, String id) throws Exception {
		if (file == null)
			throw new Exception("Arquivo nulo!");
		
		this.file = file;
		this.filename = filename;
		this.id = id;
	}
	
	public FileDataSource getDataSource() {
		return this.file;
	}

	public String getFilename() {
		return this.filename;
	}
	
	public String getName() {
		return new File(this.filename).getName();
	}
	
	public String getId() {
		return this.id;
	}
	
	public DataHandler getDataHandler() {
		return new DataHandler(this.file);
	}
	
	public String toString() {
		return "{" + this.file + ":" + this.filename + ":" + this.id + "}";
	}
	
	public boolean equals(Object obj) {
		if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        Attachment a = (Attachment) obj;
        
        if (!this.file.equals(a.file))
        	return false;
        	
        if (!this.filename.equals(a.filename))
        	return false;
        	
        if (!this.id.equals(a.id))
        	return false;
        
        return true;
	}
	
	public int hashCode() {
		int ret = 3;
		
		ret += this.file.hashCode()*7;
		ret += this.filename.hashCode()*7;
		ret += this.id.hashCode()*7;
		
		return ret;
	}
	
	public Attachment(Attachment at) throws Exception {
		if (at == null)
			throw new Exception("Modelo nulo!");
		
		this.file = at.file;
		this.filename = at.filename;
		this.id = at.id;
	}
}