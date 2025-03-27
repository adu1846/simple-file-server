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
     * Returns the file server's home directory.
     *
     * <p>This method retrieves the home directory value configured via application properties.
     *
     * @return the file server home directory as a String
     */
    public String getHome() {
        System.out.
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }
}
