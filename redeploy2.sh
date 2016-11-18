#!/bin/bash

#################################################################################
# Active Deploy Code
#################################################################################

PROJECT=${PWD##*/}
COMPONENT=${PROJECT#refarch-cloudnative-}
COMPONENT_NEW=${COMPONENT}"-new"
  
# Push application code
cf active-deploy-show ${COMPONENT} >/dev/null
RUN_RESULT=$?
if [[ ${RUN_RESULT} -ne 0 ]]; then
  echo "No active deploy of ${COMPONENT} currently exists."
else
  cf active-deploy-check-status ${COMPONENT} --status completed --quiet >/dev/null
  RUN_RESULT=$?
  if [[ ${RUN_RESULT} -eq 0 ]]; then
    cf active-deploy-delete ${COMPONENT}
    cf delete -f ${COMPONENT}
    cf rename ${COMPONENT_NEW} ${COMPONENT}
  else
    echo "Active deploy of ${COMPONENT} has not completed yet."
    cf active-deploy-show ${COMPONENT}
  fi
fi
	
