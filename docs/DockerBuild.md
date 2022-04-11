## Build and publish Dockers for x86_64 and ARM64
```
export VERSION=1.0.0
# on x86 AMD64 device:
docker build -t adu1846/simple-file-server:${VERSION}-amd64 --build-arg ARCH=amd64 --file ./Dockerfile . 
docker push adu1846/simple-file-server:${VERSION}-amd64

# on ARM64 v8 device:
docker build -t adu1846/simple-file-server:${VERSION}-arm64v8 --build-arg ARCH=arm64v8 --file ./Dockerfile .
docker push adu1846/simple-file-server:${VERSION}-arm64v8

# on x86 AMD64 device: 
docker manifest create \
adu1846/simple-file-server:${VERSION} \
--amend adu1846/simple-file-server:${VERSION}-amd64 \
--amend adu1846/simple-file-server:${VERSION}-arm64v8

docker manifest push adu1846/simple-file-server:${VERSION}
```
