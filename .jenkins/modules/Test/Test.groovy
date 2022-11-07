MPLPostStep('always') {
    cleanWs()
}

MPLPostStep('failure') {
    echo "There are test failures, archiving server log"
    archiveArtifacts artifacts: "./${getPayaraDirectoryName}/glassfish/domains/${getDomainName()}/logs/server.log"
}

withMaven(jdk: CFG.jdk, options: [artifactsPublisher(disabled: true)]) {
    sh "mvn -B -V -ff -e clean install --strict-checksums -Dsurefire.useFile=false \
                -Djavax.net.ssl.trustStore=${env.JAVA_HOME}/jre/lib/security/cacerts \
                -Djavax.xml.accessExternalSchema=all -Dpayara.version=${CFG.'build.version'} \
                -Ppayara-server-remote,stable"
}
