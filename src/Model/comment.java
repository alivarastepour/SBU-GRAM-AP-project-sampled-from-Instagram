package Model;

import java.io.Serial;
import java.io.Serializable;

/**
 *this class allows us to create objects with specified fields defined as "comments"
 */
public class comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 95607568789L;
    String author ;
    String comment ;
    
    public comment(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}
