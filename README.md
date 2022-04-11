[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java11](https://img.shields.io/badge/java-8-blue)](https://img.shields.io/badge/java-8-blue)
[![Gradle](https://img.shields.io/badge/gradle-v7.2-blue)](https://img.shields.io/badge/gradle-v7.2-blue)

# Simple File Server 
A simple file server which only has upload and download feature.

# Getting Started

### Rest Endpoints

#### Download file  
* __GET__ http://localhost:8888/** - download file on path. file must exist.   
  ``curl -X GET http://localhost:8888/path/to/data1.txt ``

#### Upload file
* __POST__ http://localhost:8888/upload/** - upload file, parent directory(ies) will be auto created  
 ``curl -u 'user:user123' -F 'file=@/path/to/data1.txt' http://localhost:8888/upload/path/to/data1.txt ``


### Run in Docker
* Run with default configuration [application.yml](src/main/resources/application.yml) - only for demo purposes.
  ```
  docker run -d --name simple-file-server \
    --restart unless-stopped \
    -e FILESERVER_HOME=/data/fileserver/  \    # 修改文件存储目录
    -e SPRING_SECURITY_USER_NAME=user \  # 修改user的名称
    -e SPRING_SECURITY_USER_PASSWORD=test123 \  # 修改user的认证密码
    -p 8888:8888 adu1846/simple-file-server:1.0.0 
  ```
* Build your own [docker image for amd64 or arm64v8](docs/DockerBuild.md) platform.

### Build and Run
Variable ``fileserver.home`` in ``application.yml`` file defines *base directory* to be exposed via REST APIs.
```
gradle clean build test
java -jar build/libs/simple-file-server-1.0.0-SNAPSHOT.jar --spring.config.location=file:./src/main/resources/application.yml
```
