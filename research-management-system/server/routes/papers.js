const express = require('express');
const router = express.Router();
const Paper = require('../models/Paper');

const searchSanitizer = (str) => 
  str ? str.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&') : '';

// 获取所有论文
router.get('/', async (req, res, next) => {
  try {
    const limit = Math.min(parseInt(req.query.limit) || 10, 100);
    const page = Math.max(parseInt(req.query.page), 1) || 1;
    const safeSearch = searchSanitizer(req.query.search);

    const query = safeSearch 
      ? { $or: [
          { title: { $regex: safeSearch, $options: 'i' } },
          { authors: { $regex: safeSearch, $options: 'i' } },
          { journal: { $regex: safeSearch, $options: 'i' } }
        ]}
      : {};

    const [papers, count] = await Promise.all([
      Paper.find(query)
        .limit(limit)
        .skip((page - 1) * limit)
        .lean(),
      Paper.countDocuments(query)
    ]);

    res.json({
      papers,
      totalPages: Math.ceil(count / limit),
      currentPage: page
    });
  } catch (err) {
    next(err);
  }
});

// 获取单个论文详情
router.get('/:id', async (req, res, next) => {
  try {
    const paper = await Paper.findById(req.params.id).lean();
    if (!paper) {
      return res.status(404).json({ error: 'Paper not found' });
    }
    res.json(paper);
  } catch (err) {
    next(err);
  }
});


// 创建论文
router.post('/', async (req, res, next) => {
  try {
    const paper = new Paper(req.body);
    const newPaper = await paper.save();
    res.status(201).json(newPaper);
  } catch (err) {
    next(err);
  }
});

// 在routes/papers.js中添加
router.get('/count', async (req, res) => {
  try {
    const count = await Paper.countDocuments();
    res.json({ count });
  } catch (err) {
    res.status(500).json({ error: '统计失败' });
  }
});

module.exports = router;