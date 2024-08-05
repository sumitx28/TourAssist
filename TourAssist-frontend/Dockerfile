FROM node:18.18

WORKDIR /app

ARG BACKEND_URL

ENV BACKEND_URL=$BACKEND_URL

RUN pwd

COPY / /app

RUN npm install

RUN npm i -g serve

RUN npm run build

EXPOSE 3000

CMD [ "serve", "-s", "dist" ]
