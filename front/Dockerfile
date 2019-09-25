# base image
FROM node:12.2.0

# set working directory
WORKDIR /front

# add `/app/node_modules/.bin` to $PATH
ENV PATH /front/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json
RUN npm install
RUN npm install -g @angular/cli@7.3.9

# add app
COPY . /front

# start app
CMD ng serve --prod --host 0.0.0.0 --port 8080

EXPOSE 8080
