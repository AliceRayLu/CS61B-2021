package gitlet;

import java.io.Serializable;

public class blob implements Serializable {
    private String name;
    private byte[] content;

    public blob(String bname,byte[] bcontent){
        name = bname;
        content = bcontent;
    }

    /** get the name of the file */
    public String getName(){
        return name;
    }

    /**get the content of the file.*/
    public byte[] getContent(){
        return content;
    }
}
