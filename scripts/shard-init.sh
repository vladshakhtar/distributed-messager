#!/bin/bash

echo "Starting sharding initialization..."

# Initialize config server replica set
echo "Initializing config server replica set..."
docker exec -i distributed-messager-config-server-1 mongosh --port 27019 <<EOF > /dev/null 2>&1
rs.initiate({
    _id: "configReplSet",
    configsvr: true,
    members: [{ _id: 0, host: "config-server:27019" }]
});
EOF
sleep 3

# Initialize shard1 replica set
echo "Initializing shard1 replica set..."
docker exec -i distributed-messager-shard1-1 mongosh --port 27018 <<EOF > /dev/null 2>&1
rs.initiate({
    _id: "shard1ReplSet",
    members: [{ _id: 0, host: "shard1:27018" }]
});
EOF
sleep 3

# Initialize shard2 replica set
echo "Initializing shard2 replica set..."
docker exec -i distributed-messager-shard2-1 mongosh --port 27017 <<EOF > /dev/null 2>&1
rs.initiate({
    _id: "shard2ReplSet",
    members: [{ _id: 0, host: "shard2:27017" }]
});
EOF
sleep 3

# Add shards to the cluster
echo "Adding shards to the cluster..."
docker exec -i distributed-messager-query-router-1 mongosh --port 27020 <<EOF > /dev/null 2>&1
sh.addShard("shard1ReplSet/shard1:27018");
sh.addShard("shard2ReplSet/shard2:27017");
EOF
sleep 3

# Enable sharding for the database
echo "Enabling sharding for the distributed-messager database..."
docker exec -i distributed-messager-query-router-1 mongosh --port 27020 <<EOF > /dev/null 2>&1
use("distributed-messager");
sh.enableSharding("distributed-messager");
EOF
sleep 3

# Run init-mongo.js to initialize collections and users
echo "Initializing collections and users from init-mongo.js..."
docker exec -i distributed-messager-query-router-1 mongosh --port 27020 /mongo-init/init-mongo.js > /dev/null 2>&1
sleep 3
# Start Reader App
echo "Starting Reader App..."
docker exec -d distributed-messager-reader-app-1 java -jar /app/app.jar 2>&1 || echo "Reader app failed to start"

# Start Writer App
echo "Starting Writer App..."
docker exec -d distributed-messager-writer-app-1 java -jar /app/app.jar 2>&1 || echo "Writer app failed to start"

# Optionally restart Nginx if necessary
echo "Restarting Nginx service..."
docker-compose restart nginx

echo "Sharding setup, database initialization, and application startup complete!"