#!/usr/bin/env bash

YML=test # without hyphen

RELAUNCH=0 #-r
MODULE=0
DATE=

while getopts rhm: opt;
do
        case $opt in
            r)
#              echo "-r was triggered!" >&2
              RELAUNCH=1
              # remove logs here
              ;;
            m)
              MODULE=${OPTARG}
              ;;
            h)
              printf "Modules we have: \n users \n companies \n campaigns \n files \n networks \n
              -r    restart flag
              -m [module name]    module name or 'all' to affect all modules"
              ;;
            \?)
              echo "Invalid option: -$OPTARG" >&2
              exit 1
              ;;
  esac
done

if [ $MODULE = users ] || [ $MODULE = all ]; then

    pkill -f -x "java -jar users.jar server $YML-users.yml"
    echo "killed users"

    if [ $RELAUNCH = 1 ]; then
       (
        cd ../users
        nohup java -jar users.jar server ${YML}-users.yml > console.log &
        exit 0
        ) &
        echo "relaunched users"
    fi

fi

if [ $MODULE = files ] || [ $MODULE = all ]; then

    pkill -f -x "java -jar files.jar server $YML-files.yml"
    echo "killed files"

    if [ $RELAUNCH = 1 ]; then
        (
        cd ../files
        nohup java -jar files.jar server ${YML}-files.yml > console.log &
        exit 0
        ) &
        echo "relaunched files"
    fi

fi

if [ $MODULE = campaigns ] || [ $MODULE = all ]; then

    pkill -f -x "java -jar campaigns.jar server $YML-campaigns.yml"
    echo "killed campaigns"

    if [ $RELAUNCH = 1 ]; then
        (
        cd ../campaigns
        nohup java -jar campaigns.jar server ${YML}-campaigns.yml > console.log &
        exit 0
        ) &
        echo "relaunched campaigns"
    fi

fi

if [ $MODULE = companies ] || [ $MODULE = all ]; then

    pkill -f -x "java -jar companies.jar server $YML-companies.yml"
    echo "killed companies"

    if [ $RELAUNCH = 1 ]; then
        (
        cd ../companies
        nohup java -jar companies.jar server ${YML}-companies.yml > console.log &
        exit 0
        ) &
        echo "relaunched companies"
    fi

fi

if [ $MODULE = networks ] || [ $MODULE = all ]; then

    pkill -f -x "java -jar networks.jar server $YML-networks.yml"
    echo "killed networks"

    if [ $RELAUNCH = 1 ]; then
        (
        cd ../networks
        nohup java -jar networks.jar server ${YML}-networks.yml > console.log &
        exit 0
        ) &
        echo "relaunched networks"
    fi

fi

if [ $MODULE = reporting ] || [ $MODULE = all ]; then

    pkill -f -x "java -jar reporting.jar server $YML-reporting.yml"
    echo "killed reporting"

    if [ $RELAUNCH = 1 ]; then
        (
        cd ../reporting
        nohup java -jar reporting.jar server ${YML}-reporting.yml > console.log &
        exit 0
        ) &
        echo "relaunched reporting"
    fi

fi

exit 0
