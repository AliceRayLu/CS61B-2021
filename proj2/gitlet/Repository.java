package gitlet;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  @author ARL
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


    /** initialize the repository: set up the .gitlet folder*/
    public static void setup(){
        if(GITLET_DIR.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }else{
            COMMIT_DIR.mkdirs();
            BLOB_DIR.mkdirs();
            STAGE_DIR.mkdirs();
            BRANCH_DIR.mkdirs();
            branch HEAD = new branch("HEAD");
            Commit initial = new Commit("initial commit",new Date(0),null);
            File iniCommit = join(COMMIT_DIR,Utils.sha1(initial.toString()));
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
        return GITLET_DIR.exists();
    }

    /** add the file to blobs, return the blob.*/
    public static blob AddToBlob(File f){
        blob newBlob = new blob(f.getName(),Utils.readContents(f));
        File blob = join(BLOB_DIR,Utils.sha1(newBlob.getName(),newBlob.getContent()));
        if(blob.exists()){
            return null;
        }else{
            Utils.writeObject(blob,newBlob);
        }
        return newBlob;
    }

    /** add to stage area*/
    public static void AddToStage(String file){
        File f = join(CWD,file);
        if(!f.exists()){
            System.out.println("File does not exist.");
        }else {
            stage newStage = new stage(file, true);
            File stageFile = join(STAGE_DIR, Utils.sha1(file));
            blob b = Repository.AddToBlob(f);
            if (b == null) {
                if (stageFile.exists()) {
                    stageFile.delete();
                }
            } else {
                newStage.changeBlob(b);
                Utils.writeObject(stageFile, newStage);
            }
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
            branch HEAD = Utils.readObject(Repository.HEAD,branch.class);
            Commit pre = HEAD.curCommit;
            Commit newCommit = new Commit(message,cur,pre);
            Set<File> preFile = pre.getFiles();
            Set<File> newCommitFile = new HashSet<>();
            List<String> stageSet = Utils.plainFilenamesIn(STAGE_DIR);
            if(!preFile.isEmpty()){
                for(File f:preFile){
                    blob b = Utils.readObject(f,blob.class);
                    if(stageSet.contains(Utils.sha1(b.getName()))){
                        File blob1 = join(STAGE_DIR,Utils.sha1(b.getName()));
                        stage s = Utils.readObject(blob1,stage.class);
                        if(s.isForAdd()){
                            blob newBlob = s.getFileBlob();
                            File newFile = join(BLOB_DIR,Utils.sha1(newBlob.getName(),newBlob.getContent()));
                            newCommitFile.add(newFile);
                        }
                        stageSet.remove(Utils.sha1(b.getName()));
                        blob1.delete();
                    }else{
                        newCommitFile.add(f);
                    }
                }
            }
            if(checkStage()){
                String[] stages = STAGE_DIR.list();
                assert stages != null;
                for(String s: stages){
                    File f = join(STAGE_DIR,s);
                    stage stageFile = Utils.readObject(f,stage.class);
                    blob newBlob = stageFile.getFileBlob();
                    File newFile = join(BLOB_DIR,Utils.sha1(newBlob.getName(),newBlob.getContent()));
                    newCommitFile.add(newFile);
                    f.delete();
                }
            }
            newCommit.addFiles(newCommitFile);
            File nc = join(COMMIT_DIR,Utils.sha1(newCommit.toString()));
            Utils.writeObject(nc,newCommit);
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

    /** check if a file is in a stage area, if in, delete*/
    public static boolean IsInStage(String name){
        String[] stages = STAGE_DIR.list();
        assert stages != null;
        for(String s:stages){
            if(s.equals(Utils.sha1(name))){
                File f = join(STAGE_DIR,s);
                f.delete();
                return true;
            }
        }
        return false;
    }

    /** check if a file is in current commit, if in, stage for removal. */
    public static boolean IsInCommit(String name){
        branch HEAD = Utils.readObject(Repository.HEAD,branch.class);
        Commit cur = HEAD.curCommit;
        for(File f: cur.getFiles()){
            blob b = Utils.readObject(f,blob.class);
            if(b.getName().equals(name)){
                stage s = new stage(name,false);
                s.changeBlob(b);
                File newStage = join(STAGE_DIR,Utils.sha1(name));
                Utils.writeObject(newStage,s);
                File rmFile = join(CWD,name);
                if(rmFile.exists()){
                    Utils.restrictedDelete(rmFile);
                }
                return true;
            }
        }
        return false;
    }

    /** print the commit log*/
    public static void printLog(){
        branch HEAD = Utils.readObject(Repository.HEAD,branch.class);
        Commit cur = HEAD.curCommit;
        while (cur != null){
            System.out.println("===");
            System.out.println("commit "+Utils.sha1(cur.toString()));
            if(cur.getMerged() != null){
                System.out.println("Merge: "+Utils.sha1(cur.getPre().toString()).substring(0,7)+" "+Utils.sha1(cur.getMerged().toString()).substring(0,7));
            }
            System.out.println("Date: "+cur.getTime());
            System.out.println(cur.getMessage());
            System.out.println();
            cur = cur.getPre();
        }
    }

    /** print all the commit */
    public static void printGlobal(){
        String[] commits = COMMIT_DIR.list();
        for(String s: commits){
            File f = join(COMMIT_DIR,s);
            Commit c = Utils.readObject(f,Commit.class);
            System.out.println("===");
            System.out.println("commit "+Utils.sha1(c.toString()));
            if(c.getMerged() != null){
                System.out.println("Merge: "+Utils.sha1(c.getPre().toString()).substring(0,7)+" "+Utils.sha1(c.getMerged().toString()).substring(0,7));
            }
            DateFormat df = DateFormat.getDateInstance();
            System.out.println("Date: "+df.format(c.getTime()));
            System.out.println(c.getMessage());
            System.out.println();
        }
    }

    /** find the commit according to commit message */
    public static void findCommit(String message){
        int flag = 0;
        String[] commits = COMMIT_DIR.list();
        for(String s: commits){
            File f = join(COMMIT_DIR,s);
            Commit c = Utils.readObject(f,Commit.class);
            if(c.getMessage().equals(message)){
                flag = 1;
                System.out.println(Utils.sha1(c.toString()));
            }
        }
        if(flag == 0){
            System.out.println("Found no commit with that message.");
        }
    }

    /** checkout a file according to commit */
    public static void checkoutFile(String cname,String filename){
        int status = 1;
        Commit c = new Commit();
        branch b = Utils.readObject(Repository.HEAD,branch.class);
        if(cname.equals("HEAD")){c = b.curCommit;}
        else{
            File f = join(COMMIT_DIR,cname);
            if(!f.exists()){
                System.out.println("No commit with that id exists.");
                status = 0;
            }else{ c = Utils.readObject(f, Commit.class);}
        }
        if(status == 0){System.exit(0);}
        Set<File> files = c.getFiles();
        for(File cblob: files){
            blob myFile = Utils.readObject(cblob,blob.class);
            if(myFile.getName().equals(filename)){
                status = 0;
                File cwdFile = join(CWD,filename);
                Utils.writeContents(cwdFile,myFile.getContent());
                break;
            }
        }
        if(status != 0){System.out.println("File does not exist in that commit.");}
    }

    /** checkout a branch */
    public static void checkoutBranch(String bname){
        File bFile = join(BRANCH_DIR,bname);
        branch head = Utils.readObject(Repository.HEAD,branch.class);
        if(!bFile.exists()){System.out.println("No such branch exists.");}
        else if(head.cur.name().equals(bname)){System.out.println("No need to checkout the current branch.");}
        else{
            branch b = Utils.readObject(bFile,branch.class);
            Commit c = b.curCommit;
            List<String> files = Utils.plainFilenamesIn(CWD);
            if(files.size() != c.getFiles().size()){System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
            }else{
                for(File f:c.getFiles()){
                    blob file = Utils.readObject(f,blob.class);
                    File cwdFile = join(CWD,file.getName());
                    Utils.writeContents(cwdFile,file.getContent());
                }
                head.cur = b;
                Utils.writeObject(Repository.HEAD,head);
            }
        }
    }
    // TODO: fill in the rest of this class.

}
