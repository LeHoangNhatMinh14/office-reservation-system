# Dockerfile for office-reservation-web
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy all files from your frontend directory to the container
COPY . .

# Install npm dependencies
RUN npm install

# Run the Vite dev server (or you could build the app and use a production server)
CMD ["npm", "run", "dev"]

# Expose port 5173 for Vite
EXPOSE 5173
