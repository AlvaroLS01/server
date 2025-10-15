FROM docker-repo.tier1.es:5043/producto/base-jdk:1.8.65

ENV COMERZZIA_HOME="${INSTALL_DIR}"

ENV APP_WAR=comerzzia-api-omnichannel-application.war \
    JAVA_OPTS="-Doracle.jdbc.timezoneAsRegion=false -Dfile.encoding=UTF-8"

COPY ["./application/src/main/docker/setup-app.sh", "./application/src/main/docker/comerzzia.xml", "./application/target/${APP_WAR}", "${INSTALL_DIR}/"]

COPY ["./resources", "${INSTALL_DIR}/resources"]

CMD "${INSTALL_DIR}/java/bin/java" ${JAVA_OPTS} -Xmx${JAVA_OPTS_MX}m -classpath resources:lib/*:lib/ext/*:${APP_WAR} org.springframework.boot.loader.WarLauncher
