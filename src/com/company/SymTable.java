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

//TODO: Can we move HashMaps to the end instead of the beginning?
public class SymTable {
    private ArrayList<HashMap<String,Sym>> mapList;

    public SymTable(){
        mapList = new ArrayList<HashMap<String,Sym>>();
        mapList.add(new HashMap<String, Sym>());
    }

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

    public void addScope(){
        mapList.add(0, new HashMap<String, Sym>());
    }

    public Sym lookupLocal(String name) throws EmptySymTableException{
        if(mapList.isEmpty()){
            throw new EmptySymTableException();
        }else if(mapList.get(0).containsKey(name)){
            return mapList.get(0).get(name);
        }else{
            return null;
        }
    }

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

    public void removeScope() throws EmptySymTableException{
        if(mapList.isEmpty()){
            throw new EmptySymTableException();
        }else{
            mapList.remove(0);
        }
    }

    public void print(){
        System.out.println("\nSymb Table");

        for (HashMap<String, Sym> map: mapList){
            System.out.println(map.toString() + '\n');
        }
        System.out.println("\n");

    }

}
