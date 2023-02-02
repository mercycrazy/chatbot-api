IMAGE_TAG="1.0"
APP_NAME="chatbot-api"

# 构建可执行 Jar
mvn clean package

# 打包镜像
docker build -f ./Dockerfile -t mercycrazy/chatbot-api:${IMAGE_TAG} .

# 上传镜像
echo aa677877+ | docker login --password-stdin -u mercycrazy
docker push mercycrazy/chatbot-api:${IMAGE_TAG}

CONTAINER_ID=$(docker ps -a | grep ${APP_NAME} | grep -v grep | awk '{print $1}')
if [ ! "$CONTAINER_ID" ]; then
  docker rm -f $APP_NAME
fi

ssh root@124.220.158.175 "docker run -p 8090:8090 --name ${APP_NAME} -d ${APP_NAME}:${IMAGE_TAG}"

