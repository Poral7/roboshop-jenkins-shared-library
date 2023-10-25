def compile () {
    if (app_lang == "nodejs") {
        sh 'npm install'

    }
    if (app_lang == "maven") {
        sh 'mvn package'

    }
}

def unittests() {
    if (app_lang == "nodejs") {

/*
        Developer is missing unit test cases in our project,they need to add them as best practices, we are skipping to proced further
*/      sh 'npm test'
    }

    if (app_lang == "maven") {
        sh 'mvn test'

    }
    if (app_lang == "python") {
        sh 'python3 -m unittest'

    }
}

def email(email_note) {
    sh 'echo $(email_note}'
}

