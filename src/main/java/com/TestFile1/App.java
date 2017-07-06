package com.TestFile1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{




    public static void main( String[] args )
    {
        ListFilesInf listFiles = new ListFiles();

        System.out.println( "Hello World!" );

        // listFiles.listFiles("/Users/amittawade/");
        // listFiles.listFilesAndFilesSubDirectories("/Users/amittawade");
        // listFiles.listFilesAndFolders("/Users/amittawade/IdeaProjects/testFile1");
        List<MyFile> myFileList = listFiles.listFiles("/Users/amittawade/IdeaProjects/testFile1");

        for(MyFile myfile : myFileList)
        {
            System.out.println("Name: " + myfile.getName() + " Type: " + myfile.getType() +  " Size: " + myfile.getSize() +
                    " mimeType: " + myfile.getMimeType() + " supported: " + myfile.isSupported() );
        }

        List<MyFile> myFileList1 = listFiles.listUnsupportedFileTypes("/Users/amittawade/IdeaProjects/testFile1", "iml,xml");

        for(MyFile myfile : myFileList1)
        {
            System.out.println("Name: " + myfile.getName() + " Type: " + myfile.getType() +  " Size: " + myfile.getSize() +
                    " mimeType: " + myfile.getMimeType() + " supported: " + myfile.isSupported() );
        }


        /*try(List<Path> pathList = Files.list(Paths.get("")))  {

        }
        catch (IOException e) {
            System.err.println(e.getMessage());

        }*/

/*


        try (Stream<Path> stream = Files.list(Paths.get(""))) {

            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> !path.startsWith("."))
                    .sorted()
                    .collect(Collectors.joining("; "));
            System.out.println("List: " + joined);
        }

        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

        Path start = Paths.get("");
        int maxDepth = 1;
        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String supportedMimeTypes = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".xml"))
                    .sorted()
                    .collect(Collectors.joining("; "));
            System.out.println("Supported: " + supportedMimeTypes);
        }

        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String unSupportedMimeTypes = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".iml"))
                    .sorted()
                    .collect(Collectors.joining("; "));
            System.out.println("Unsupported" + unSupportedMimeTypes);
        }

        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
*/


    }





}
