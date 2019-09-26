# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
FROM tiangolo/node-frontend:10 as build-stage

WORKDIR /front

COPY package*.json /front/

RUN npm install

COPY ./ /front/

ARG configuration=production

RUN npm run build -- --output-path=./dist/out --configuration $configuration

# Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
FROM nginx:1.15

COPY --from=build-stage /front/dist/out/ /usr/share/nginx/html
