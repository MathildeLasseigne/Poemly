package tests;

import widgets.tools.FileListManager;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class TestTxtReader {



    public static void main(String[] args){
        List l = FileListManager.readFileInList("src/assets/tests/testReader.txt");

        Iterator<String> itr = l.iterator();
        while (itr.hasNext())
            System.out.print(itr.next());
    }

}
