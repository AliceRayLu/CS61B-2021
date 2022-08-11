package gitlet;

import java.io.Serializable;

public class stage implements Serializable {
    private String fileName;
    private blob fileBlob;
    /** if true then the file is to be added, false for removal.*/
    private boolean forAdd;

    public stage(String name,boolean isForAdd){
        fileName = name;
        forAdd = isForAdd;
        fileBlob = new blob();
    }

    public void changeBlob(blob b){
        fileBlob = b;
    }

    public String getFileName(){
        return fileName;
    }
    public boolean isForAdd(){
        return forAdd;
    }

    public blob getFileBlob(){
        return fileBlob;
    }
}
