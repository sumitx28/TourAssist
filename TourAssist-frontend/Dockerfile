FROM node:18.18

RUN pwd

WORKDIR /app

RUN pwd

COPY / /app

RUN npm install

RUN npm i -g serve

ENV BACKEND_URL=$BACKEND_URL

RUN npm run build

EXPOSE 3000

CMD [ "serve", "-s", "dist" ]
