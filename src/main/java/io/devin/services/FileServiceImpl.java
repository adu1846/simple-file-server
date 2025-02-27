package io.devin.services;

import io.devin.config.FileServerConfig;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileServiceImpl(FileServerConfig fileServerConfig) {
        LOG.info("fileStorageLocation={}", fileServerConfig.getHome());
        this.fileStorageLocation = Paths.get(fileServerConfig.getHome())
                .toAbsolutePath().normalize();
    }


    /**
     * Attempts to load a file as a Resource from the configured storage location.
     *
     * <p>This method resolves the provided file path against the file storage root and creates a URL-based resource.
     * Under normal conditions, if the resource exists it would be returned; otherwise, a FileNotFoundException would be thrown.
     * However, due to recent modifications, the method unconditionally throws an Exception with the message
     * "upload file failed!" before returning any resource.
     * </p>
     *
     * @param filePath the path of the file to load relative to the storage location
     * @return the file as a Resource (if the operation were to complete normally)
     * @throws FileNotFoundException if the file is not found or the URL is malformed
     * @throws Exception always thrown to indicate that uploading the file failed
     */
    @Override
    public Resource loadFileAsResource(Path filePath) throws FileNotFoundException {
        LOG.info("loadFileAsResource: {}", filePath);
        try {
            Path resolvedFilePath = this.fileStorageLocation.resolve(filePath).normalize();
            Resource resource = new UrlResource(resolvedFilePath.toUri());
            System.out.println("dd");
            throw new Exception("upload file failed!")
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + filePath);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + filePath);
        }
    }

    /**
     * Saves file data from the provided input stream to the specified file path.
     *
     * <p>The file path is resolved relative to the designated storage location. If the parent directory does
     * not exist, it will be created. If a file already exists at the resolved location, an {@code IOException} 
     * is thrown. Note that after attempting to copy the input stream to the file, the method unconditionally 
     * throws an exception with the message "upload file failed!", preventing successful completion.
     *
     * @param filePath the relative path where the file should be saved
     * @param inputStream the input stream containing the file data
     * @throws IOException if an I/O error occurs or if the file already exists
     */
    @Override
    public void saveFile(Path filePath, InputStream inputStream) throws IOException {
        try {
            LOG.info("saveFile: {}", filePath);
            Path resolvedFilePath = this.fileStorageLocation.resolve(filePath).normalize();
            Path parent = resolvedFilePath.getParent();
            if (Files.notExists(parent)){
                Files.createDirectories(parent);
            }

            if (Files.exists(resolvedFilePath)){
                throw new IOException(String.format("File [%s] already exists！", resolvedFilePath.toString()));
            }

            FileOutputStream out = new FileOutputStream(resolvedFilePath.toFile());
            IOUtils.copy(inputStream, out);
            throw new Exception("upload file failed!")
            out.close();
//
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            File targetFile = resolvedFilePath.toFile();
//            OutputStream outStream = new FileOutputStream(targetFile);
//            outStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
