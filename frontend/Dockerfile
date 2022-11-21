FROM trion/ng-cli:15.0.0 as build
WORKDIR /app
COPY ./src ./src
COPY ./angular.json ./angular.json
COPY ./package.json ./package.json
COPY ./tsconfig.app.json ./tsconfig.app.json
COPY ./tsconfig.json ./tsconfig.json
COPY ./tsconfig.spec.json ./tsconfig.spec.json

RUN npm install
RUN npm run build:prod

FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY ./cert.pem /etc/nginx/certs/cert.pem
COPY ./key.pem /etc/nginx/certs/key.pem
EXPOSE 443
