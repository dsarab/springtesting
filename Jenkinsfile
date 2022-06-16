
pipeline {
    agent any

    options {
            ansiColor('xterm')
            timestamps ()
            disableConcurrentBuilds ()
            buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        }

    stages {
        stage('Build') {
            steps {
                 //sh "./gradlew test assemble"
                    withGradle {
                      sh "./gradlew test assemble check"
                    }
            }
            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    archiveArtifacts 'build/libs/*.jar'
                    jacoco()
                    recordIssues(tools: [pmdParser(pattern: '/build/reports/pmd/*.xml')])
                }

            }
        }
        stage('Publish') {
             steps{
                sshagent(['github-ssh']){
                    sh 'git tag BUILD-1.0.${BUILD_NUMBER}'
                    sh 'git push --tags'
                }
            }
        }
    }
}