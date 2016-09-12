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

    public static void main(String[] args) throws IOException{
        ArrayList<String> pageLines = openFile();

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
