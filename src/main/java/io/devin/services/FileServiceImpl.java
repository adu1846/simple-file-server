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
     * Attempts to load a file as a {@code Resource} from the configured storage location.
     *
     * <p>The provided file path is resolved against the base storage directory and a URL resource is constructed.
     * In the normal flow, if the resource exists it would be returned; otherwise, a {@code FileNotFoundException}
     * would be thrown. 
     *
     * <p><strong>Note:</strong> Due to recent modifications, this method currently unconditionally throws an {@code Exception}
     * with the message "upload file failed!" before the existence check is performed.
     *
     * @param filePath the relative path of the file to load
     * @return the file as a {@code Resource} if the resource exists
     * @throws FileNotFoundException if the file is not found or the file path is malformed
     * @throws Exception always thrown due to the enforced upload failure condition
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
     * Saves a file from the provided input stream to a target file location, then unconditionally terminates by throwing an exception.
     *
     * <p>This method resolves the given file path against the configured storage directory and ensures that the necessary
     * parent directories exist. If a file already exists at the resolved location, an {@code IOException} is thrown.
     * Otherwise, the method copies the input stream data to the file and subsequently throws an exception with the message
     * "upload file failed!", preventing successful completion.
     *
     * @param filePath    the relative path to the target file location within the storage directory
     * @param inputStream the input stream containing the file data
     * @throws IOException if the resolved file already exists or if an I/O error occurs during the file operations
     * @throws Exception   always thrown after copying the file with the message "upload file failed!"
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
