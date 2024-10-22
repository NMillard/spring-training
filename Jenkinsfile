echo "hello"

pipeline {
    agent { docker "maven" }
    
    stages {
         stage("checkout") {
             steps {
                sh "git config --global --add safe.directory '*'"
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/NMillard/spring-training'
                sh "ls"    
             }
        }
        
        stage("build") {
            steps {
                sh "./mvnw clean compile package"
                sh "ls"    
             }
        }
        
        stage("archive") {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', followSymlinks: false   
             }
        }   
    }
}