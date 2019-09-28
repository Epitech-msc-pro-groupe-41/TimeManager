#!/bin/bash

cd ./data/certbot/conf/live/timemanager.francecentral.cloudapp.azure.com

sudo openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root
