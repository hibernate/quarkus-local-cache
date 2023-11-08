@Library('hibernate-jenkins-pipeline-helpers@1.7') _

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
	parameters {
		string(
				name: 'RELEASE_VERSION',
				defaultValue: '',
				description: 'The version to be released, e.g. 0.4.0.',
				trim: true
		)
		string(
				name: 'DEVELOPMENT_VERSION',
				defaultValue: '',
				description: 'The next version to be used after the release, e.g. 0.4.1-SNAPSHOT.',
				trim: true
		)
		booleanParam(
				name: 'RELEASE_DRY_RUN',
				defaultValue: false,
				description: 'If true, just simulate the release, without pushing any commits or tags, and without uploading any artifacts or documentation.'
		)
	}
	stages {
		stage('Build') {
			steps {
				withMaven(mavenLocalRepo: env.WORKSPACE_TMP + '/.m2repository') {
					sh "mvn clean verify"
				}
			}
		}
		stage('Release') {
			when {
				beforeAgent true
				// Releases must be triggered explicitly with parameters
				expression { return params.RELEASE_VERSION }
			}
			steps {
				script {
					// Check that all the necessary parameters are set
					if (!params.RELEASE_VERSION) {
						throw new IllegalArgumentException("Missing value for parameter RELEASE_VERSION.")
					}
					if (!params.DEVELOPMENT_VERSION) {
						throw new IllegalArgumentException("Missing value for parameter DEVELOPMENT_VERSION.")
					}

					def releaseVersion = Version.parseReleaseVersion(params.RELEASE_VERSION, Version.Scheme.JBOSS_NO_FINAL)
					def developmentVersion = Version.parseDevelopmentVersion(params.DEVELOPMENT_VERSION, Version.Scheme.JBOSS_NO_FINAL)
					echo "Performing full release for version ${releaseVersion.toString()}"

					withMaven(mavenSettingsConfig: params.RELEASE_DRY_RUN ? null : 'ci-hibernate.deploy.settings.maven',
							mavenLocalRepo: env.WORKSPACE_TMP + '/.m2repository') {
						configFileProvider([configFile(fileId: 'release.config.ssh', targetLocation: env.HOME + '/.ssh/config'),
											configFile(fileId: 'release.config.ssh.knownhosts', targetLocation: env.HOME + '/.ssh/known_hosts')]) {
							sshagent(['ed25519.Hibernate-CI.github.com']) {
								sh 'cat $HOME/.ssh/config'
								sh """ \
                                    mvn release:prepare \
                                    -Dtag=${releaseVersion.toString()} \
                                    -DreleaseVersion=${releaseVersion.toString()} \
                                    -DdevelopmentVersion=${developmentVersion.toString()} \
                                """
								sh """ \
                                    mvn release:perform ${params.RELEASE_DRY_RUN ? '-DdryRun' : ''} \
                                """
							}
						}
					}
				}
			}
		}
	}
}
