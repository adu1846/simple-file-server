package io.devin.services;


import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Service for manipulating files on the file system.
 */
public interface FileService {

    /**
     * Create {@link Resource} for given file.
     * @param filePath relative path to file.
     * @return {@link Resource} representing the file.
     * @throws FileNotFoundException
     * @throws OperationNotAllowedException
     */
    Resource loadFileAsResource( Path filePath) throws FileNotFoundException, OperationNotAllowedException;


    /**
     * Writes data in {@link InputStream} into file specified by relative path.
     * @param filePath relative path to file.
     * @param inputStream data to be written into that file.
     * @throws IOException
     * @throws OperationNotAllowedException
     */
    void saveFile(Path filePath, InputStream inputStream) throws IOException, OperationNotAllowedException;

}
