package com.company;

/**
 * Created by:    kartikayalasomayajula
 * Date Created:  9/8/16.
 * E-Mail:        ayalasomayaj@wisc.edu
 * Course:        CS 536-Compilers - 001
 * Project        1A
 *
 * Class:         Sym.java
 * Purpose:       Define an individual Sym (symbol) along with its attributes.
 */
public class Sym {
    String sym;

    public Sym(String type){
        this.sym = type;
    }

    public String getType(){
        return this.sym;
    }

    public String toString(){
        //TODO: Add more to this later
        return this.sym;
    }
}
