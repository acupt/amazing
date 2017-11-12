#!/bin/bash
git pull
if [ $? -ne 0 ]; then
	echo "pull failed"
	exit 2;
fi
mvn clean package -Dmaven.test.skip=true
if [ $? -ne 0 ]; then
	echo "package failed"
	exit 2;
fi
nohup java -jar target/amazing-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
echo "finish"

