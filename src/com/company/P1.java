package com.company;
/**
 * Created by:    kartikayalasomayajula
 * Date Created:  9/8/16.
 * E-Mail:        ayalasomayaj@wisc.edu
 * Course:        CS 536-Compilers - 001
 * Project        1A
 *
 * Class:         P1.Java
 * Purpose:       Main Function to test functionality of Sym and SymTable.
 */
import java.io.*;
import java.util.*;

public class P1 {
    //1. Test Duplicate Sym Exc
    //2. Test EmptySymExc

    public static void main(String[] args) throws IOException, DuplicateSymException, EmptySymTableException{
        ArrayList<String> pageLines = openFile();

        symTest();
        addDeclTest();
        scopeTests();

    }

    public static void lookupTest() throws EmptySymTableException, DuplicateSymException{
        String file = "SymTable.java";
        String method = "lookupLocal(String name)";

        //Test if EmptySymTableException returns when searching table without scopes.
        try{
            SymTable emptySymTable = new SymTable();
            emptySymTable.addDecl("int", new Sym("int"));
            emptySymTable.removeScope();
            emptySymTable.lookupLocal("int");
            //EmptySymTableException expected
            error(file, method, "EmptySymTableException expected but not received");
        }catch(DuplicateSymException e){
            error(file, "addDecl", "Invalid duplicate exception thrown when testing emptyTable Exception for lookupLocal()");
        }catch(EmptySymTableException e){
            //expected
        }catch(Exception e){
            error(file, method, "Incorrect exception thrown when testing for EmptySymTableException.");
        }

        SymTable symTable = new SymTable();

        //Test if correct key returns:
        try{
            symTable.addDecl("int", new Sym("int"));
            Sym receivedSym = symTable.lookupLocal("int");
            if(receivedSym == null){
                error(file, method, "Not able to receive result when attempting to lookup locally");
            }else if(receivedSym.getType() != "int"){
                error(file, method, "Invalid Sym type being received when attempting to lookup Sym on local scope");
            }
        }catch(Exception e){
            error(file, method, "Invalid exception type being caught when attempting to look up on local scope");
        }

        //Test if invalid key returns when bad key passed in:
        try{
            Sym receivedSym = symTable.lookupLocal("double");
            //null expected
            if(receivedSym != null){
                error(file, method, "Invalid result returned when attempting to look up key that does not exist, locally. Null expected");
            }
        }catch(Exception e){
            error(file, method, "Unexpected and invalid error caught when attempting to look up non-existant key, locally");
        }

    }

    public static void symTest(){
        String[] types = {"Int", "String", "Bool", ""};
        String file = "Sym.java";
        for(String type : types){
            Sym newSym = new Sym(type);
            if(!newSym.getType().equals(type)){
                error(file, "getType()", "Invalid type is being set and returned");
            }else if(!newSym.toString().equals(type)){
                error(file, "toString()", "Inconsistent value being returned.");
            }
        }
    }

    public static void addDeclTest() throws DuplicateSymException, EmptySymTableException{
        SymTable symTable = new SymTable();
        Sym[] symList = {new Sym("int"), new Sym("double"), new Sym("boolean")};
        String[] symWordList = {"int", "double","boolean"};
        int index = 0;
        String file = "SymTable.java";
        String method = "addDecl()";
        //Add new Sym.
        try{
            for (Sym sym: symList){
                //Adding a new Symbol.
                symTable.addDecl(symWordList[index], sym);
                index++;
            }
        }catch(Exception e){
            error(file, method, "Unable to add a new Declaration into Hash Table");
        }

        //test for DuplicateSymException:
        try{
            symTable.addDecl("int", symList[0]);
            //DuplicateSymException expected here
            error(file, method, "DuplicateSymException expected but never received.");
        }catch(DuplicateSymException e){
            //This is expected
        }catch(Exception e){
            error(file, method, "Inconsistent exception called when trying to add a Duplicate.");
        }

        //test for NullPointerException:
        try{
            symTable.addDecl(null, null);
            //NullPointerException expected here
            error(file, method, "NullPointerException expected but never received.");
        }catch(NullPointerException e){
            //This is expected
        }catch(Exception e){
            error(file, method, "Inconsistent exception called when trying to cause Null Pointer Exception.");
        }

        //test for emptyException:
        try{
            SymTable symTable2 = new SymTable();
            symTable2.removeScope();
            symTable2.addDecl("tester", new Sym("tester"));
            //An error is expected here:
            error(file, method, "No EmptySymTableException thrown when attempting to add on empty list of hashmaps");
        }catch(EmptySymTableException e){
            //This should occur.
        }catch(Exception e){
            error(file, method, "Inconsistent exception called when trying to add declaration with 0 hashmaps available");
        }

        //test if element might be able to be added on a different scope.
        try{
            //A new scope should be added to the front.
            symTable.addScope();
            symTable.addDecl("int", symList[0]);
            //No Duplicate Error expected here
        }catch(DuplicateSymException e){
            error(file, "addScope()", "DuplicateSymException should not have been called when adding duplicate on different scope.");
        }catch(Exception e){
            error(file, "addScope()", "Inconsistent exception called when trying to add a Duplicate.");
        }
    }

    public static void scopeTests() throws EmptySymTableException{
        SymTable symTable = new SymTable();
        Sym[] symList = {new Sym("int"), new Sym("double"), new Sym("boolean")};
        String[] symWordList = {"int", "double","boolean"};
        int index = 0;
        String file = "SymTable.java";
        //Add new Sym.
        try{
            symTable.removeScope();
            //ArrayList has 0 scopes now.
            symTable.removeScope();
            //Error expected
            error(file, "removeScope()", "EmptySymTableException expected after removing scope from empty list but not received.");
        }catch(EmptySymTableException e){
            //Error expected
        }catch(Exception e){
            error(file, "removeScope()", "Unidentified exception thrown, yet not expected when trying to remove scope with no scopes. ");
        }
    }

    public static void error(String file, String errMethod, String err){
        System.out.println("error found: <File: " + file + " Method:" + errMethod + "> " + err);
    }

    public static ArrayList<String> openFile() throws IOException{

        // The name of the file to open.
        String fileName = "resources/P1InputFile.txt";
        ArrayList<String> pageLines = new ArrayList<String>();

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fr =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fr);

            while((line = bufferedReader.readLine()) != null) {
                pageLines.add(line);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch(IOException ex) {
            System.out.println("Error reading file");
        }

        return pageLines;

    }
}
