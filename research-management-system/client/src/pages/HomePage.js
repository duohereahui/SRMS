import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Statistic } from 'antd';
import axios from 'axios';

const HomePage = () => {
  const [paperCount, setPaperCount] = useState(0);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const res = await axios.get('/api/papers/count');
        setPaperCount(res.data.count);
      } catch (err) {
        console.error('获取统计数据失败:', err);
      }
    };
    fetchStats();
  }, []);

  return (
    <div style={{ padding: 24 }}>
      <h1>学院科研成果概览</h1>
      <Row gutter={16}>
        <Col span={8}>
          <Card>
            <Statistic title="论文数量" value={paperCount} />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default HomePage;