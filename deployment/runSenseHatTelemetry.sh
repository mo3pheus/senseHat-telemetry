#!/usr/bin/env bash

source /etc/environment
source /etc/senseHatTelemetry/senseHatTelemetry.conf

echo ${MQTT_TRANSMISSION_ENDPOINT}

echo "Executing scheduled data poll service"
java -jar /opt/senseHatTelemetry/senseHat-telemetry-1.0-shaded.jar \
  --collection.interval.seconds "${COLLECTION_INTERVAL_SECONDS}" \
  --sensehat.data.fields "${SENSEHAT_DATA_FIELDS}" \
  --mqtt.transmission.topic "${MQTT_TRANSMISSION_TOPIC}" \
  --mqtt.transmission.endpoint "${MQTT_TRANSMISSION_ENDPOINT}" \
  --log.level "${LOG_LEVEL}" \
  --log.directory "${LOG_DIRECTORY}"
