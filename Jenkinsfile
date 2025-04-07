@Library('hibernate-jenkins-pipeline-helpers') _

import org.hibernate.jenkins.pipeline.helpers.version.Version

pipeline {
	agent {
		label 'Worker&&Containers'
	}
	tools {
		maven 'Apache Maven 3.9'
		jdk 'OpenJDK 17 Latest'
	}
	options {
		buildDiscarder logRotator(daysToKeepStr: '30', numToKeepStr: '10')
		disableConcurrentBuilds(abortPrevious: false)
	}
	stages {
		stage('Build') {
			steps {
				withMaven(mavenLocalRepo: env.WORKSPACE_TMP + '/.m2repository') {
					sh "mvn clean verify"
				}
			}
		}
	}
}
