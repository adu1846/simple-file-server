package io.devin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "fileserver")
public class FileServerConfig {

    @Value("${fileserver.home}")
    private String home;

    /**
     * Retrieves the configured home directory for the file server.
     *
     * @return the file server's home directory
     */
    public String getHome() {
        System.out.
        return home;
    }

    /**
     * Sets the file server's home directory.
     *
     * @param home the directory path to set as the file server's home
     */
    public void setHome(String home) {
        this.home = home;
    }
}
