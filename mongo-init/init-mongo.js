db.createUser({
    user: "app",
    pwd: "apppassword",
    roles: [
      {
        role: "readWrite",
        db: "distributed-messager"
      }
    ]
});

// Switch to the application database
db = db.getSiblingDB('distributed-messager');

// Create collections
db.createCollection('users');
db.createCollection('chats');
db.createCollection('messages');

db.users.createIndex({ username: 1 }, { unique: true });
db.messages.createIndex({ chatId: 1, timestamp: 1 });