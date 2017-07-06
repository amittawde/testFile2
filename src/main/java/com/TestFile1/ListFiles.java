package com.TestFile1;

/**
 * Created by amittawade on 05/07/2017.
 */


        import org.apache.tika.Tika;
        import org.apache.tika.mime.MimeTypes;

        import javax.activation.MimetypesFileTypeMap;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.net.URLConnection;
        import java.nio.file.Files;
        import java.nio.file.LinkOption;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.text.SimpleDateFormat;
        import java.util.*;

        import java.util.logging.*;
        import java.util.logging.Formatter;


public class ListFiles implements  ListFilesInf {

    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;

    private List<MyFile> myFileList;
    private List<MyFile> unsupportedFileList;
    private List<MyFile> supportedFileList;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /**
     * Constructor
     */

    ListFiles(){

        try {
            logger.setLevel(Level.FINE);

            String tmpDir = System.getProperty("java.io.tmpdir");

            fileTxt = new FileHandler(tmpDir + "Logging.txt");

            // create a TXT formatter
            formatterTxt = new SimpleFormatter();
            //fileTxt.setFormatter(formatterTxt);
            fileTxt.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return record.getLevel()
                            + logTime.format(cal.getTime())
                            + " || "
                            + record.getSourceClassName().substring(
                            record.getSourceClassName().lastIndexOf(".")+1,
                            record.getSourceClassName().length())
                            + "."
                            + record.getSourceMethodName()
                            + "() : "
                            + record.getMessage() + "\n";
                }
            });

            logger.addHandler(fileTxt);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /* Initialise the fileLists */
        supportedFileList = new ArrayList<MyFile>();
        myFileList = new ArrayList<MyFile>();
        unsupportedFileList = new ArrayList<MyFile>();
    }


    private Tika mimeTika = new Tika();

    /**
     * List all the files under a directory and return a list of supported and unsupported files
     * @param directoryName to be listed
     * @return List<MyFile> - List of files</MyFile>
     */
    public List<MyFile> listFiles(String directoryName) {

        Path directoryPath = Paths.get(directoryName);

        File directory = null;

        if(Files.exists(directoryPath,LinkOption.NOFOLLOW_LINKS))
        {

            directory = new File(directoryName);

            //get all the files from a directory
            File[] fList = directory.listFiles();
            String mimeType3 = null;
            String type = null;

            logger.info("FileList in the directory: " + directoryName);
            logger.info("Filename   Extension   Mimetype    Supported");

            for (File file : fList) {

                if (file.isFile()) {


                    try {
                        mimeType3 = mimeTika.detect(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    type = getFileExtension(file);


                    myFileList.add(new MyFile(file.getName(), type, mimeType3, file.length(), true));
                    logger.info(file.getName() + " " +  type + " " + mimeType3 + " " + file.length() + " true");
                }
            }
            return myFileList;
        }
        else
            return null;
    }


    /**
     * List all the files under a directory and return a list of supported and unsupported files
     * @param directoryName to be listed
     * @param unsupportedFileType one or more comma separated unsupported file extensions
     * @return List<MyFile> - List of supported and unsupported files</MyFile>
     */
    public List<MyFile> listUnsupportedFileTypes(String directoryName, String unsupportedFileType)
    {

        myFileList = this.listFiles(directoryName);

        String[] unsupTypes = unsupportedFileType.split(",");
        logger.info("FileList supported and unsupported files in: " + directoryName);
        logger.info("Filename   Extension   Mimetype    Supported");

        for(String unsupType : unsupTypes)
        {
            for (MyFile myfile : myFileList)
            {
                if(myfile.getType().equals(unsupType))
                {
                    myfile.setSupported(false);
                    unsupportedFileList.add(myfile);
                    logger.info(myfile.getName() + " " +  myfile.getType() + " " + myfile.getMimeType() + " " + myfile.getSize() + " false");
                }
                else {
                    supportedFileList.add(myfile);
                    logger.info(myfile.getName() + " " + myfile.getType() + " " + myfile.getMimeType() + " " + myfile.getSize() + " true");
                }
            }

        }

        return myFileList;

    }


    /**
     * List all the files under a directory
     * @param directoryName to be listed
     */
    /*public void listFiles(String directoryName){
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                System.out.println(file.getName());
            }
        }
    }*/




    /**
     * List all the folder under a directory
     * @param directoryName to be listed
     */
    public void listFolders(String directoryName){
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isDirectory()){
                System.out.println(file.getName());
            }
        }
    }
    /**
     * List all files from a directory and its subdirectories
     * @param directoryName to be listed
     */
    public void listFilesAndFilesSubDirectories(String directoryName){
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()){
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }

    private String getFileExtension(File file) {
        String  fileName = file.getName();
        if (file.isDirectory())
            return "directory";
        else if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
