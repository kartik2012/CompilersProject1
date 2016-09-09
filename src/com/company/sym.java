package com.company;

/**
 * Created by:    kartikayalasomayajula
 * Date Created:  9/8/16.
 * E-Mail:        ayalasomayaj@wisc.edu
 * Course:        CS 536-Compilers - 001
 * Project        1A
 *
 * Class:         sym.java
 * Purpose:       Define an individual sym (symbol) along with its attributes.
 */
public class sym {
    String sym;

    public sym(String type){
        this.sym = type;
    }

    public String getType(){
        return this.sym;
    }

    public String toString(){
        //TODO: Add more to this later
        return sym;
    }
}
