#!/bin/bash

is_ready() {
    eval "[ $(curl --write-out %{http_code} --silent --output /dev/null http://elasticsearch:9200/_cat/health?h=st) = 200 ]"
}

# wait until is ready
i=0
while ! is_ready; do
    i=`expr $i + 1`
    if [ $i -ge 5 ]; then
        echo "$(date) - still not ready, giving up"
        exit 1
    fi
    echo "$(date) - waiting to be ready"
    sleep 2
done

#start the script
exec npm run prod