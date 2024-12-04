ROOT_FOLDER=/Users/vladshakhtar/Downloads/distributed-messager-main/distributed-messager

cd $ROOT_FOLDER

docker-compose down

cd $ROOT_FOLDER/writer-app
./gradlew clean build

cp -r build/libs/writer-app-0.0.1-SNAPSHOT.jar $ROOT_FOLDER/jars/writer.jar

cd $ROOT_FOLDER/reader-app
./gradlew clean build

cp -r build/libs/reader-app-0.0.1-SNAPSHOT.jar $ROOT_FOLDER/jars/reader.jar

cd $ROOT_FOLDER

docker-compose up