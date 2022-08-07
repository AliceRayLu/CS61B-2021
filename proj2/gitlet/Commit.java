package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author ARL
 */
public class Commit implements Serializable {
    /**
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The time of this commit.*/
    private Date time;
    /** previous commit */
    private Commit pre;
    /** merged commit */
    private Commit merged;
    /** the file content in this commit */
    private Set<File> files;

    public Commit(String cmessage,Date ctime,Commit cpre){
        message = cmessage;
        time = ctime;
        pre = cpre;
        merged = null;
    }

    public Set<File> getFiles(){
        return files;
    }

    public Commit getPre(){
        return pre;
    }

    public Commit getMerged(){
        return merged;
    }

    public void addFiles(Set<File> files){
        this.files = files;
    }

    public Date getTime(){
        return time;
    }

    public String getMessage(){
        return message;
    }

    /* TODO: fill in the rest of this class. */
}
