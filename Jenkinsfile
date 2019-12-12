pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '/opt/Sterling/runtime/bin/sci_ant.sh -f /opt/Sterling/runtime/devtoolkit/devtoolkit_extensions.xml importfromproject -Dprojectdir=${WORKSPACE}'
      }
    }

  }
}