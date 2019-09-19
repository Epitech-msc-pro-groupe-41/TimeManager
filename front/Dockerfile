# Use an official node runtime as a parent image
FROM node

# Set the working directory
WORKDIR /front

# A wildcard is used to ensure both package.json AND package-lock.json are copied
COPY package*.json ./

# Install dependencies
RUN npm install

#Copy the current directory contents into the container
COPY . .

# The container listens on the specified network ports at runtime
EXPOSE 8080

# Run app when the container launches
CMD [ "npm", "start" ]
