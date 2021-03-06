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
                 sh "./gradlew test assemble"
            }
            post {
                success {
                    junit 'build/test-results/test/*.xml'
                    archiveArtifacts 'build/libs/*.jar'
                    jacoco()
                    recordIssues(tools: [pmdParser(pattern: 'build/reports/pmd/*.xml')])
                    recordIssues(tools: [pit(pattern: 'build/reports/pitest/*.xml')])
                }
            }
        }


        //stage('SonarQube analysis') {
        //     steps {
        //        withSonarQubeEnv('sonarqube') {
        //             sh './gradlew sonarqube'
        //        }
        //     }
        //}

        stage('Publish') {
            steps{
                withGradle {
                    withCredentials([usernamePassword(credentialsId: 'git.token', passwordVariable: 'TOKEN', usernameVariable: 'USERNAME'), usernamePassword(credentialsId: 'nexus', passwordVariable: 'TOKENN', usernameVariable: 'USERNAMEN')]) {
                        sh "./gradlew publish"
                    }
                }
                sshagent(['github-ssh2']){
                    sh 'git tag BUILD-1.0.${BUILD_NUMBER}'
                    sh 'git push --tags'
                }
            }
        }
    }
}