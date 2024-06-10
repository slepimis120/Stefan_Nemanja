# Stage 1: Build the React app
FROM node:latest as build-deps
WORKDIR /usr/src/app
COPY package.json yarn.lock ./
RUN yarn install
COPY . ./
RUN yarn build

# Stage 2: Serve the React app
FROM node:latest
WORKDIR /usr/src/app
COPY --from=build-deps /usr/src/app ./
EXPOSE 3000
CMD ["yarn", "start"]
