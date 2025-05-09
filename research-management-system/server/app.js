require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const helmet = require('helmet');

const app = express();

// 中间件配置
app.use(cors({
  origin: process.env.FRONTEND_URL || 'http://localhost:3000',
  optionsSuccessStatus: 200
}));
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// 数据库连接
const connectDB = async () => {
  try {
    await mongoose.connect(process.env.MONGODB_URI || 'mongodb://127.0.0.1:27017/research_db', {
      useNewUrlParser: true,
      useUnifiedTopology: true,
      serverSelectionTimeoutMS: 5000,
    });
    console.log('✅ MongoDB连接成功');
  } catch (err) {
    console.error('❌ MongoDB连接失败:', err.message);
    process.exit(1);
  }
};
connectDB();

// 路由配置
app.use('/api/papers', require('./routes/papers'));

// 错误处理中间件
app.use((err, req, res, next) => {
  console.error(err.stack);
  
  if (err.name === 'ValidationError') {
    return res.status(400).json({ 
      error: 'Validation Failed',
      details: err.errors 
    });
  }

  res.status(500).json({ 
    error: 'Internal Server Error',
    message: err.message 
  });
});

// 启动服务器
const PORT = process.env.PORT || 5000;
const server = app.listen(PORT, () => {
  console.log(`🚀 服务器已启动: http://localhost:${PORT}`);
});

process.on('SIGTERM', () => {
  server.close(() => {
    mongoose.connection.close();
    console.log('服务器已关闭');
  });
});