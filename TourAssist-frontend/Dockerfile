FROM node:18.18

RUN pwd

WORKDIR /app

RUN pwd

COPY / /app

RUN npm install

RUN npm i -g serve

RUN npm run build

EXPOSE 3000

CMD [ "serve", "-s", "dist" ]
