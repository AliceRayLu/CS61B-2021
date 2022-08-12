package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author ARL
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Please enter a command.");
        }else{
            String firstArg = args[0];
            switch(firstArg) {
                case "init":
                    if(args.length > 1){
                        System.out.println("Incorrect operands.");
                    }else{
                        Repository.setup();
                    }
                    break;
                case "add":
                    if(args.length != 2){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.AddToStage(args[1]);
                    }
                    break;
                case "commit":
                    if(args.length < 2 || args[1].equals("")){
                        System.out.println("Please enter a commit message.");
                    }else if(args.length > 2){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else if(!Repository.checkStage()){
                        System.out.println("No changes added to the commit.");
                    }else{
                        Repository.MakeCommit(args[1]);
                    }
                    break;
                case "rm":
                    if(args.length != 2){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        if(!Repository.IsInStage(args[1]) && !Repository.IsInCommit(args[1])){
                            System.out.println("No reason to remove the file.");
                        }
                    }
                    break;
                case "log":
                    if(args.length > 1){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.printLog();
                    }
                    break;
                case "global-log":
                    if(args.length > 1){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.printGlobal();
                    }
                    break;
                case "checkout":
                    if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else if(args.length == 3){
                        if(!args[1].equals("--")){
                            System.out.println("Incorrect operands.");
                        }else {
                            Repository.checkoutFile("HEAD", args[2]);
                        }
                    }else if(args.length == 4){
                        if(!args[2].equals("--")){
                            System.out.println("Incorrect operands.");
                        }else{
                            Repository.checkoutFile(args[1],args[3]);
                        }
                    }else{
                        if(args.length != 2){
                            System.out.println("Incorrect operands.");
                        }else{
                            Repository.checkoutBranch(args[1]);
                        }
                    }
                    break;
                case "find":
                    if(args.length != 2){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.findCommit(args[1]);
                    }
                    break;
                case "status":
                    if(args.length != 1){
                        System.out.println("Incorrect operands.");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.printStatus();
                    }
                    break;
                case "branch":
                    if(args.length != 2){
                        System.out.println("Incorrect operands");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.addBranch(args[1]);
                    }
                    break;
                case "rm-branch":
                    if(args.length != 2){
                        System.out.println("Incorrect operands");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.removeBranch(args[1]);
                    }
                    break;
                case "reset":
                    if(args.length != 2){
                        System.out.println("Incorrect operands");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.resetCommit(args[1]);
                    }
                    break;
                case "merge":
                    if(args.length != 2){
                        System.out.println("Incorrect operands");
                    }else if(!Repository.isInitialized()){
                        System.out.println("Not in an initialized Gitlet directory.");
                    }else{
                        Repository.merge(args[1]);
                    }
                    break;
                default:
                    System.out.println("No command with that name exists.");
                    break;
            }
        }

    }
}
