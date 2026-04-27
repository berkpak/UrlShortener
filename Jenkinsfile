pipeline {
    agent any

    tools {
        jdk 'jdk17'
    }

    environment {
        JAVA_HOME = tool 'jdk17'
        APP_NAME = "url-shortener"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/berkpak/UrlShortener.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                   docker build -t ${APP_NAME}:${IMAGE_TAG} .
                   docker tag ${APP_NAME}:${IMAGE_TAG} ${APP_NAME}:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                   docker-compose down || true
                   docker-compose up -d --no-build app db
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                   sleep 15
                   curl --fail http://app:8080/actuator/health
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline Success'
        }

        failure {
            echo 'Pipeline Failed'
        }

        always {
            sh 'docker ps || true'
        }
    }
}