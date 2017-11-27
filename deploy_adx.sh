#!/usr/bin/env bash

KEYPATH=/home/denis/projects/adlogix/GTG.pem
LOCALPATH=/home/denis/projects/adlogix
REMOTEPATH=ubuntu@54.173.68.88:/home/ubuntu/adx
YML=test # without hyphen

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/kill-modules.sh ${REMOTEPATH}/scripts/

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/campaigns/target/campaigns.jar ${REMOTEPATH}/campaigns/
rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/campaigns/target/${YML}-campaigns.yml ${REMOTEPATH}/campaigns/

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/companies/target/companies.jar ${REMOTEPATH}/companies/
rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/companies/target/${YML}-companies.yml ${REMOTEPATH}/companies/

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/files/target/files.jar ${REMOTEPATH}/files/
rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/files/target/${YML}-files.yml ${REMOTEPATH}/files/

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/networks/target/networks.jar ${REMOTEPATH}/networks/
rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/networks/target/${YML}-networks.yml ${REMOTEPATH}/networks/

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/users/target/users.jar ${REMOTEPATH}/users/
rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/users/target/${YML}-users.yml ${REMOTEPATH}/users/

rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/reporting/target/reporting.jar ${REMOTEPATH}/reporting/
rsync -rave "ssh -i $KEYPATH" ${LOCALPATH}/reporting/target/${YML}-reporting.yml ${REMOTEPATH}/reporting/
