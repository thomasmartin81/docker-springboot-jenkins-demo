pipeline {
    agent any

    environment {
        dockerBuilderImageName = 'maven:3-openjdk-11'
        dockerPullRegistryUrl = 'http://192.168.20.14:8083'
        dockerPushRegistryUrl = 'http://192.168.20.14:8082'
        dockerRegistryCredentialsId = 'devopvm-docker-registry'
        dockerBuildImageName = 'docker-springboot-demo'
        dockerContainerName = 'docker-springboot-demo'
    }

    stages {
        stage('prepare docker builder') {
            agent {
                docker {
                    image "${dockerBuilderImageName}"
                    args '-v /opt/maven:/root/.m2'
                    registryUrl "${dockerPullRegistryUrl}"
                    registryCredentialsId  "${dockerRegistryCredentialsId}"
                }
            }
            stages {
                stage('compile code') {
                     steps {
                         echo 'compile code'
                         script {
                             sh 'mvn clean compile'
                         }
                     }
                 }
                stage('run local tests') {
                    steps {
                        echo 'run local tests'
                        script {
                            sh 'mvn test -Plocal-tests'
                        }
                    }
                }

                stage('copy dependencies') {
                    when { branch 'master' }
                    steps {
                        echo 'copy dependencies'
                        script {
                            sh 'mvn dependency:copy-dependencies -DincludeScope=runtime'
                        }
                    }
                }
            }
        }

        stage('build and push docker image') {
            when { branch 'master' }
            steps {
                echo 'build and push docker image'
                script {
                    docker.withRegistry("${dockerPullRegistryUrl}", "${dockerRegistryCredentialsId}") {
                        dockerImage = docker.build("${dockerBuildImageName}", "./")
                    }
                    docker.withRegistry("${dockerPushRegistryUrl}", "${dockerRegistryCredentialsId}") {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('deploy') {
            when { branch 'master' }
            steps {
                echo 'redeploy new container'
                script {
                    sh """
                        docker rm -f ${dockerContainerName} 2&> /dev/null || true
                        docker run --rm -d -p 80:8080 --name ${dockerContainerName} ${dockerBuildImageName}
                        """
                }
            }
        }
    }
}
