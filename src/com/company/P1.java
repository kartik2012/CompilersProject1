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
public class P1 {
    public static void main(String[] args) throws DuplicateSymException, EmptySymTableException{
        symTest();
        addDeclTest();
        scopeTests();
        localLookupTest();
        globalLookupTest();
        printTest();
    }

    /*
    Purpose: Test for a range of erros within the local Lookup.
     */
    public static void localLookupTest() throws EmptySymTableException, DuplicateSymException{
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

        //Test if adding a scope impacts the local search.
        try{
            symTable.addScope();
            //Should not find anything
            if(symTable.lookupLocal("int") != null){
                error(file, method, "Expected a null when attempting to do localSearch() after adsding a new scope, but received an invalid value");
            }
        }catch(Exception e){
            error(file, method, "Invalid exception caught when attmepting to do local search after adding a new scope");
        }
    }

    /*
    Purpose: Test for a range of errors within the global Lookup.
    */
    public static void globalLookupTest() throws EmptySymTableException, DuplicateSymException{

        String file = "SymTable.java";
        String method = "lookupGlobal(String name)";

        //Test if EmptySymTableException returns when searching table without scopes.
        try{
            SymTable emptySymTable = new SymTable();
            emptySymTable.addDecl("int", new Sym("int"));
            emptySymTable.removeScope();
            emptySymTable.lookupGlobal("int");
            //EmptySymTableException expected
            error(file, method, "EmptySymTableException expected but not received");
        }catch(DuplicateSymException e){
            error(file, "addDecl", "Invalid duplicate exception thrown when testing emptyTable Exception for lookupGlobal()");
        }catch(EmptySymTableException e){
            //expected
        }catch(Exception e){
            error(file, method, "Incorrect exception thrown when testing for EmptySymTableException.");
        }

        //Search all of the hashmaps for the Symbol.
        SymTable symTable = new SymTable();
        try{
            symTable.addDecl("Int", new Sym("Int"));
            symTable.addDecl("Double", new Sym("Double"));
            symTable.addScope();
            symTable.addDecl("Int2", new Sym("Int2"));
            symTable.addDecl("Double2", new Sym("Double2"));
            symTable.addScope();
            boolean truth = true;
            symTable.addDecl("Int3", new Sym("Int3"));
            symTable.addDecl("Double3", new Sym("Double3"));
            if(symTable.lookupGlobal("Int") == null) truth = false;
            if(symTable.lookupGlobal("Double") == null) truth = false;
            if(symTable.lookupGlobal("Int2") == null) truth = false;
            if(symTable.lookupGlobal("Double2") == null) truth = false;
            if(symTable.lookupGlobal("Int3") == null) truth = false;
            if(symTable.lookupGlobal("Double3") == null) truth = false;

            if(!truth){
                error(file, method, "Unexpected null statement received when attempting to perform a global search");
            }
        }catch(EmptySymTableException e){
            error(file, method, "Unexpected and invalid EmptySymTableException caught when attempting to perform valid global search");
        }catch(Exception e){
            error(file, method, "Unexpected and invalid exception caught when attempting to perform valid global search.");
        }

        try{
            if(symTable.lookupGlobal("Harambe") != null){
                error(file, method, "Expecting null return when looking up invalid key, yet received a non-null return value. ");
            }
            //expecting null
        }catch(Exception e){
            //Should not error out.
            error(file, method, "Unexpected error caught when attempting to test false null value");
        }
    }

    /*
    Purpose: Test for a range of errors within the sym class and corresponding tests.
    */
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

    /*
    Purpose: Test for a range of errors within the addDeclaration methods
    */
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

    /*
    Purpose: Test for a range of errors within the scope methods
    */
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

    /*
    Purpose: Test Printing functionality.
    */
    public static void printTest() throws EmptySymTableException, DuplicateSymException{
        SymTable symTable = new SymTable();
        String file = "SymTable.java";
        String method = "print()";
        System.out.println(">>Expected Print:\n>>>SymTable\n>>>{}\nActual Print:");
        try{
            symTable.print();
        }catch(Exception e){
            error(file, method, "Unexpected error found when attempting to print");
        }

        try{
            symTable.addDecl("Int", new Sym("int"));
            symTable.addScope();
            symTable.addDecl("Double", new Sym("Double"));
            System.out.println(">>Expected Print:\n>>>SymTable\n>>>{Double=Double}\n>>>\n>>>{Int=int}\nActualPrint:");
            symTable.print();
            System.out.println(">>Testing print statement for Sym\n>>> Expected: Double\nActual:");
            System.out.println(symTable.lookupGlobal("Double").toString());
        }catch(Exception e){
            error(file, method, "Unexpected error found when attempting to print");
        }

    }

    /*
    Purpose: Standardized prompt for printing out errors.
    */
    public static void error(String file, String errMethod, String err){
        System.out.println("error found: <File: " + file + " Method:" + errMethod + "> " + err);
    }
}
