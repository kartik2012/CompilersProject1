package com.company;

import java.util.*;

/**
 * Created by:    kartikayalasomayajula
 * Date Created:  9/8/16.
 * E-Mail:        ayalasomayaj@wisc.edu
 * Course:        CS 536-Compilers - 001
 * Project        1A
 *
 * Class:         SymTable.Java
 * Purpose:       Holds Symbol Table's main attributes.
 */

public class SymTable {
    private ArrayList<HashMap<String,Sym>> mapList;

    /*
    Purpose: Constructor for the Symbol Table.
            Initializes a new Hashmap within the arraylist.
    */
    public SymTable(){
        mapList = new ArrayList<HashMap<String,Sym>>();
        mapList.add(new HashMap<String, Sym>());
    }

    /*
    Purpose: Test for a range of errors within the global Lookup.
    Paramaters: {String name} the name of the key the user wishes to set.
                {Sym symbol} The symbol the user wishes to associate with the key.
    */
    public void addDecl(String name, Sym symbol) throws DuplicateSymException, EmptySymTableException{
        if(mapList.isEmpty()){
            throw new EmptySymTableException();
        }else if(name == null || symbol == null){
            throw new NullPointerException();
        }else if(mapList.get(0).containsKey(name)){
            throw new DuplicateSymException();
        }else{
            mapList.get(0).put(name, symbol);
        }
    }

    /*
    Purpose: Adds another scope by setting another HashMap to the front of the list.
    */
    public void addScope(){
        mapList.add(0, new HashMap<String, Sym>());
    }

    /*
    Purpose: Performs a local lookup at the front-most HashMap for a Symbol.
    Paramaters: {String name} defines the key of the element one wishes to
                look up.
    Return:     Sym-> The Symbol that the user is attempting to find.
    */
    public Sym lookupLocal(String name) throws EmptySymTableException{
        if(mapList.isEmpty()){
            throw new EmptySymTableException();
        }else if(mapList.get(0).containsKey(name)){
            return mapList.get(0).get(name);
        }else{
            return null;
        }
    }

    /*
    Purpose: Performs a global lookup into all of the hashmaps in the list.
    Paramaters: {String name} defines the key of the element one wishes to
            look up.
    Return:     Sym-> The Symbol that the user is attempting to find.
    */
    public Sym lookupGlobal(String name) throws EmptySymTableException{
        if(mapList.isEmpty()){
            throw new EmptySymTableException();
        }else{
            for (HashMap<String, Sym> map : mapList) {
                if(map.containsKey(name)){
                    return map.get(name);
                }
            }
        }
        return null;
    }

    /*
    Purpose: Removes a hashmap from the front of the list.
    */
    public void removeScope() throws EmptySymTableException{
        if(mapList.isEmpty()){
            throw new EmptySymTableException();
        }else{
            mapList.remove(0);
        }
    }

    /*
    Purpose: Prints out the contents of the Symbol Table.
    */
    public void print(){
        System.out.println("\nSymb Table");

        for (HashMap<String, Sym> map: mapList){
            System.out.println(map.toString() + '\n');
        }
        System.out.println("\n");

    }

}
