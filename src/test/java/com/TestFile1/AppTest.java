package com.TestFile1;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    private ListFilesInf listFilesInf;

    @Override
    protected void setUp() throws Exception{
        listFilesInf = new ListFiles();
    }


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );

    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */

    public void testApp()
    {

        List<MyFile> myFileList = listFilesInf.listFiles("/Users/amittawade/IdeaProjects/testFile1/src/test/resources");

        assertTrue( "There are files in the directory", (myFileList.size() > 0) );
        //System.out.println("testApp ran");
    }

    public void testCheckDirectoryShouldExist()
    {

        List<MyFile> myFileList = listFilesInf.listFiles("/Users/amittawade/IdeaProjects/testFil");


        assertTrue( "Directory does not exist", (myFileList == null) );
        //System.out.println("testCheckDirectoryShouldExist");

    }

    public void testShouldHaveUnsupportedFiles()
    {

        String unsupportedFileTypes = "xml,docx";
        int countUnsuportedFiles = 0;

        List<MyFile> myFileList = listFilesInf.listUnsupportedFileTypes("/Users/amittawade/IdeaProjects/testFile1/src/test/resources", unsupportedFileTypes);

        for(MyFile myFile : myFileList)
        {
            if(myFile.isSupported() == false)
                countUnsuportedFiles ++;
        }

        assertTrue( "unsupportedFiles:" + unsupportedFileTypes, (countUnsuportedFiles > 0) );
        //System.out.println("testShouldHaveUnsupportedFiles " + countUnsuportedFiles);
    }


}
