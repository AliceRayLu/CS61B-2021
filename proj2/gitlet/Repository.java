package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** the HEAD file */
    public static File HEAD = join(GITLET_DIR,"HEAD");
    /** log file */
    public static File log = join(GITLET_DIR,"log");
    /** .gitlet -- /branch directory. */
    public static final File BRANCH_DIR = join(GITLET_DIR,"branch");
    /** the main master branch*/
    public static File master = join(BRANCH_DIR,"master");
    /** .gitlet -- /commit directory.*/
    public static final File COMMIT_DIR = join(GITLET_DIR,"commit");
    /** .gitlet -- /blob directory */
    public static final File BLOB_DIR = join(GITLET_DIR,"blob");
    /** .gitlet -- /blob/stage directory */
    public static final File STAGE_DIR = join(GITLET_DIR,"stage");
    /** store the staged files*/
    public static Vector<stage> stages;

    /** initialize the repository: set up the .gitlet folder*/
    public static void setup(){
        if(GITLET_DIR.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }else{
            COMMIT_DIR.mkdirs();
            BLOB_DIR.mkdirs();
            STAGE_DIR.mkdirs();
            BRANCH_DIR.mkdirs();
            if(!master.exists()){
                try {
                    master.createNewFile();
                }catch (IOException e){}
            }
            if(!HEAD.exists()){
                try {
                    HEAD.createNewFile();
                }catch (IOException e){}
            }
            branch HEAD = new branch("HEAD");
            Commit initial = new Commit("initial commit",new Date(0),null);
            File iniCommit = join(COMMIT_DIR,sha1(initial));
            branch master = new branch("master");
            master.curCommit = initial;
            HEAD.curCommit = initial;
            HEAD.cur = master;
            Utils.writeObject(Repository.HEAD,HEAD);
            Utils.writeObject(Repository.master,master);
            Utils.writeObject(iniCommit,initial);
        }
    }

    /** check if the repository is initialized.*/
    public static boolean isInitialized(){
        if(!GITLET_DIR.exists()){
            return false;
        }
        return true;
    }

    /** add the file to blobs, return the blob.*/
    public static blob AddToBlob(File f){
        blob newBlob = new blob(f.getName(),Utils.readContents(f));
        File blob = join(BLOB_DIR,Utils.sha1(newBlob.getContent()));
        if(blob.exists()){
            return null;
        }else{
            Utils.writeObject(blob,newBlob);
        }
        return newBlob;
    }

    /** add to stage area*/
    public static void AddToStage(File f){
        stage newStage = new stage(f.getName(),true);
        File stageFile = join(STAGE_DIR,Utils.sha1(f.getName()));
        blob b = AddToBlob(f);
        if(b == null){
            if(stageFile.exists()){
                stageFile.delete();
                stages.remove(newStage);
            }
        }else{
            if(stages.contains(newStage)){
                stages.remove(newStage);
                newStage.changeBlob(b);
            }
            stages.add(newStage);
            Utils.writeObject(stageFile,newStage);
        }
    }

    /** check the stage area, if nothing to be added return false.*/
    public static boolean checkStage(){
        String[] stages = STAGE_DIR.list();
        if(stages.length == 0){
            return false;
        }
        return true;
    }

    /** Make commit to the head pointer.*/
    public static void MakeCommit(String message){
        if(checkStage()){
            Date cur = new Date();
            Commit pre = Utils.readObject(HEAD,Commit.class);
            Commit newCommit = new Commit(message,cur,pre);
            Set<File> preFile = pre.getFiles();
            Set<File> newCommitFile = new HashSet<>();
            List<String> stageSet = Arrays.asList(STAGE_DIR.list());
            for(File f:preFile){
                blob b = Utils.readObject(f,blob.class);
                if(stageSet.contains(Utils.sha1(b.getName()))){
                    File blob = join(STAGE_DIR,Utils.sha1(b.getName()));
                    stage s = Utils.readObject(blob,stage.class);
                    if(s.isForAdd()){
                        newCommitFile.add(blob);
                    }
                    stageSet.remove(Utils.sha1(b.getName()));
                    blob.delete();
                }else{
                    newCommitFile.add(f);
                }
            }
            if(checkStage()){
                String[] stages = STAGE_DIR.list();
                for(String s: stages){
                    File f = join(STAGE_DIR,s);
                    newCommitFile.add(f);
                    f.delete();
                }
            }
            newCommit.addFiles(newCommitFile);
            File nc = join(COMMIT_DIR,Utils.sha1(newCommit));
            Utils.writeObject(nc,newCommit);
            branch HEAD = Utils.readObject(Repository.HEAD,branch.class);
            HEAD.curCommit = newCommit;
            Utils.writeObject(Repository.HEAD,HEAD);
            branch curBranch = HEAD.cur;
            curBranch.curCommit = newCommit;
            File curb = join(BRANCH_DIR,curBranch.name());
            Utils.writeObject(curb,curBranch);
        }else{
            System.out.println("No changes added to the commit.");
        }
    }
    // TODO: fill in the rest of this class.

}
