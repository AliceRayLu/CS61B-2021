package gitlet;

import java.io.Serializable;

public class branch implements Serializable {
    /** the branch name */
    private final String name;

    /** the current commit */
    public Commit curCommit;

    /** for HEAD pointer which tells the current branch*/
    public branch cur;

    public branch(String name){
        this.name = name;
    }

    public String name(){
        return name;
    }
}
