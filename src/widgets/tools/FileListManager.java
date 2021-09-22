package widgets.tools;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

/**
 * Manage reading a file and putting its content into a list
 * & writting a list content into a file
 */
public class FileListManager
{
    //This class was taken from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

    /**
     * Read a file and put all its content into a String List
     * @param fileName file path
     * @return
     */
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

    /**
     * Write data into a file from an array list
     * @param fileName
     * @param append true if appending text to the end of an existing file ranther than the begining
     * @param data The data to write. Each row is a different line
     */
    public static void writeFileFromList(String fileName, boolean append, ArrayList<String> data){
        try {
            FileWriter writer = new FileWriter(fileName, append);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            if(append){
                bufferedWriter.newLine();
            }
            bufferedWriter.write(data.get(0));
            for (int i = 1; i< data.size(); i++) {
                bufferedWriter.newLine();
                bufferedWriter.write(data.get(i));
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
