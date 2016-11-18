#!/bin/bash

SERVICE_DISCOVERY_UPS="eureka-service-discovery"
CONFIG_SERVER_UPS="config-server"

#################################################################################
# Active Deploy Code
#################################################################################

# Determine which JAR file we should use (since we have both Gradle and Maven possibilities)
RUNNABLE_JAR="$(find . -name "*-SNAPSHOT.jar" | sed -n 1p)"

PROJECT=${PWD##*/}
COMPONENT=${PROJECT#refarch-cloudnative-}
COMPONENT_NEW=${COMPONENT}"-new"
 
ROUTE=$(cf app ${COMPONENT}|grep 'urls'|awk '{print $2}')
HOST=${ROUTE%%.*}
DOMAIN=${ROUTE#*.}

cf app ${COMPONENT} >/dev/null
RUN_RESULT=$?
if [[ ${RUN_RESULT} -ne 0 ]]; then
  echo "No version of ${COMPONENT} currently deployed."
else
  cf active-deploy-show ${COMPONENT} >/dev/null
  RUN_RESULT=$?
  if [[ ${RUN_RESULT} -eq 0 ]]; then
    echo "An active deploy of ${COMPONENT} currently exists."
  else
	# Push application code
	cf push ${COMPONENT_NEW} -p ${RUNNABLE_JAR} -d ${DOMAIN} -n ${HOST} --no-start
	cf set-env ${COMPONENT_NEW} SPRING_PROFILES_ACTIVE cloud
	cf bind-service ${COMPONENT_NEW} ${SERVICE_DISCOVERY_UPS}
	cf bind-service ${COMPONENT_NEW} ${CONFIG_SERVER_UPS}
	cf start ${COMPONENT_NEW}
	cf unmap-route ${COMPONENT_NEW} ${DOMAIN} --hostname ${HOST}
	cf active-deploy-create ${COMPONENT} ${COMPONENT_NEW} --label ${COMPONENT} --rampup 1m --test 1m --rampdown 1m
  fi
fi