pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '/opt/Sterling/devtoolkit_docker/runtime/bin/sci_ant.sh -f /opt/Sterling/devtoolkit_docker/runtime/devtoolkit/devtoolkit_extensions.xml importfromproject -Dprojectdir=${WORKSPACE}'
      }
    }

    stage('Deploy') {
      steps {
        sh 'docker cp /opt/Sterling/devtoolkit_docker/runtime/jar/yfsextn/1_0/yfsextn.jar om-runtime:/opt/SSFS_9.5/runtime/external_deployments/smcfs.ear/lib/yfsextn.jar'
        sh 'docker cp /opt/Sterling/devtoolkit_docker/runtime/jar/yfsextn/1_0/yfsextn.jar om-runtime:/opt/SSFS_9.5/runtime/jar/yfsextn/1_0/yfsextn.jar'
      }
    }

  }
}