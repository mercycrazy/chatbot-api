IMAGE_TAG="1.0"
APP_NAME="chatbot-api"
IMAGE_NAME="mercycrazy/${APP_NAME}:${IMAGE_TAG}"

# 构建可执行 Jar
mvn clean package

# 打包镜像
docker build -f ./Dockerfile -t ${IMAGE_NAME} .

# 上传镜像
echo aa677877+ | docker login --password-stdin -u mercycrazy
docker push ${IMAGE_NAME}

# 启动服务
ssh root@124.220.158.175 "docker rm -f ${APP_NAME}; docker run -p 8090:8090 --name ${APP_NAME} -d ${IMAGE_NAME}"

