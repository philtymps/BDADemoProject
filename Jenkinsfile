pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '/opt/Sterling/runtime/bin/sci_ant.sh -f /opt/Sterling/runtime/devtoolkit/devtoolkit_extensions.xml importfromproject -Dprojectdir=${WORKSPACE}'
      }
    }

    stage('Deploy') {
      steps {
        sh 'docker cp /opt/Sterling/runtime/jar/yfsextn/1_0/yfsextn.jar om-runtime:/opt/SSFS_9.5/runtime/external_deployments/smcfs.ear/lib/yfsextn.jar'
        sh 'docker cp /opt/Sterling/runtime/jar/yfsextn/1_0/yfsextn.jar om-runtime:/opt/SSFS_9.5/runtime/jar/yfsextn/1_0/yfsextn.jar'
      }
    }

  }
}