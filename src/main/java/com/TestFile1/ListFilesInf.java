package com.TestFile1;

import java.util.List;

/**
 * Created by amittawade on 06/07/2017.
 */
public interface ListFilesInf {

    public List<MyFile> listFiles(String directoryName);
    public List<MyFile> listUnsupportedFileTypes(String directoryName, String unsupportedFileType);

}
