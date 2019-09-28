FROM node:12.2.0

WORKDIR /front

COPY package*.json /front/

RUN npm install

COPY ./ /front/

# The container listens on the specified network ports at runtime
EXPOSE 8080

# Run app when the container launches
CMD [ "npm", "start" ]
