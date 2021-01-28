FROM node:12.10

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY package*.json /usr/src/app/
RUN npm install
COPY . /usr/src/app

RUN npm run tsc

COPY . .

RUN chmod 777 wait-for-elasticsearch.sh

EXPOSE 9001

CMD ["./wait-for-elasticsearch.sh"]