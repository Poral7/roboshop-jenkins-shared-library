def call() {
    pipeline {

        agent {
            label 'workstation'
        }

        stages {

            stage('compile/Build') {
                steps {
                    script {
                        common.compile()
                    }
                }
            }

            stage('unit tests') {
                steps {
                    script {
                        common.unittests()
                }
            }

            stage('quality control') {
                steps {
                    echo 'quality control'
                }
            }

            stage('upload code to centralized place') {
                steps {
                    echo 'upload'
                }
            }

        }
    }
}