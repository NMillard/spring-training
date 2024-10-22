# Spring demonstration

## Jenkins build pipeline

```shell
# For macos
docker run -d --name jenkins-spring-demo -p 8089:8080 \
-v ./jenkins:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /usr/local/bin/docker:/usr/bin/docker \
jenkins/jenkins:lts
```