FROM node:18.18

RUN pwd

WORKDIR /app

COPY / /app

RUN npm install

EXPOSE 5173

ENTRYPOINT ["npm", "run", "dev"]