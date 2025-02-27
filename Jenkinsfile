pipeline {
    agent any
    environment {
        JASYPT_PASSWORD = credentials('JASYPT_PASSWORD') //
        JASYPT_ALGORITHM = credentials('JASYPT_ALGORITHM')
    }
    stages {
        stage('Build') {
            steps {
                sh '''
                    rm -f build/libs/*.jar
                    mvn clean package -Djasypt.encryptor.password=$JASYPT_PASSWORD \
                    -Djasypt.encryptor.algorithm=$JASYPT_ALGORITHM
                '''
            }
        }
    }
}
