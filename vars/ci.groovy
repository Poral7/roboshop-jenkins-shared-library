def call() {
    if(!env.SONAR_EXTRA_OPTS) {
        env.SONAR_EXTRA_OPTS = " "
    }
    try {
        node('workstation') {

            stage('checkout') {
                cleanWs()
                git branch: 'main', url: "https://github.com/Poral7/${component}"
            }
            stage('compile/Build') {
                common.compile()
            }

            stage('unit tests') {
                common.unittests()
            }

            stage('quality control') {
                SONAR_PASS = sh (script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                SONAR_USER = sh (script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                  /*  sh "sonar-scanner -Dsonar.host.url=http://172.31.41.67:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"*/
                    sh "echo sonar scan"
                }
            }

            stage('upload code to centralized place') {
                echo 'upload'
            }
        }
    }

    catch(Exception e) {
        common.email("failed")
    }
}



