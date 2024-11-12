# Dockerfile for office-reservation-web
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy from package.json to the container
COPY package.json .

# Install npm dependencies
RUN npm install

# Copy all files from your frontend directory to the container
COPY . .

# Expose port 5173 for Vite
EXPOSE 5173

# Run the Vite dev server (or you could build the app and use a production server)
CMD ["npm", "run", "dev"]
