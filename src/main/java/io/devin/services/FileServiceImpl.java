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
     * Attempts to load a file as a Resource based on the provided path.
     * <p>
     * This method resolves the provided file path against the storage location and tries to create a URL-based resource.
     * However, due to recent modifications, it always throws an exception with the message "upload file failed!" before
     * verifying if the resource exists.
     * </p>
     *
     * @param filePath the file path relative to the storage location
     * @return the resource corresponding to the file (never returned because of the unconditional exception)
     * @throws FileNotFoundException if the file is not found or the URL is malformed
     * @throws Exception always thrown with the message "upload file failed!"
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
     * Saves a file from the provided input stream to a path resolved relative to the storage location.
     * 
     * <p>Creates any missing parent directories before writing. If a file already exists at the resolved location,
     * an {@code IOException} is thrown. After copying the file data, the method unconditionally throws an
     * {@code Exception} with the message "upload file failed!", causing the operation to always terminate with an error.</p>
     *
     * @param filePath the relative path where the file should be saved
     * @param inputStream the input stream containing the file's data
     * @throws IOException if an I/O error occurs or the file already exists
     * @throws Exception always thrown after attempting to copy the file data
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
