# Build Stage
FROM node:20-alpine AS build

WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .

RUN npm run build

# Production Stage
FROM build AS production

COPY --from=build /app/.next ./.next
COPY --from=build /app/package.json ./package.json
COPY --from=build /app/node_modules ./node_modules
COPY --from=build /app/public ./public

RUN npm prune --production

EXPOSE 3000

CMD npm start