package tests;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class TestTxtReader {



    public static void main(String[] args){
        List l = ReadFileIntoList.readFileInList("src/assets/tests/testReader.txt");

        Iterator<String> itr = l.iterator();
        while (itr.hasNext())
            System.out.println(itr.next());
    }


    static class ReadFileIntoList
    {
        public static List<String> readFileInList(String fileName)
        {

            List<String> lines = Collections.emptyList();
            try
            {
                lines =
                        Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            }

            catch (IOException e)
            {

                // do something
                e.printStackTrace();
            }
            return lines;
        }
    }
}
