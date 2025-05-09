import React, { useState, useEffect, useCallback } from 'react';
import { Table, Input, Button, Space, DatePicker, message } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const { RangePicker } = DatePicker;
const { Search } = Input;

const PapersPage = () => {
  const [papers, setPapers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
    showSizeChanger: true,
    pageSizeOptions: ['10', '20', '50'],
    showTotal: total => `共 ${total} 条`
  });
  const [searchText, setSearchText] = useState('');
  const navigate = useNavigate();

  const columns = [
    {
      title: '标题',
      dataIndex: 'title',
      key: 'title',
      render: (text, record) => <Link to={`/papers/${record._id}`}>{text || '-'}</Link>
    },
    {
      title: '作者',
      dataIndex: 'authors',
      key: 'authors',
      render: authors => authors?.join(', ') || '-'
    },
    {
      title: '发表时间',
      dataIndex: 'publishDate',
      key: 'publishDate',
      render: date => date ? new Date(date).toLocaleDateString() : '-'
    },
    {
      title: '期刊/会议',
      dataIndex: 'journal',
      key: 'journal',
      render: text => text || '-'
    }
  ];

  const fetchPapers = useCallback(async () => {
    setLoading(true);
    try {
      const { current, pageSize } = pagination;
      const response = await axios.get('/api/papers', {
        params: {
          page: current,
          limit: pageSize,
          search: searchText
        }
      });
      
      setPapers(response.data.papers);
      setPagination(prev => ({
        ...prev,
        total: response.data.totalPages * pageSize
      }));
    } catch (err) {
      console.error('获取论文失败:', err);
      message.error('获取论文数据失败');
    } finally {
      setLoading(false);
    }
  }, [searchText, pagination.current, pagination.pageSize]);

  useEffect(() => {
    fetchPapers();
  }, [fetchPapers]);

  const handleTableChange = (newPagination) => {
    setPagination(prev => ({ ...prev, ...newPagination }));
  };

  const onSearch = (value) => {
    setSearchText(value);
    setPagination(prev => ({ ...prev, current: 1 }));
  };

  const handleAddPaper = () => {
    navigate('/papers/create');
  };

  return (
    <div style={{ padding: 24 }}>
      <h1>论文列表</h1>
      <Space style={{ marginBottom: 16 }}>
        <Search
          placeholder="搜索论文标题/作者/期刊"
          allowClear
          enterButton={<SearchOutlined />}
          onSearch={onSearch}
          style={{ width: 300 }}
        />
        <RangePicker />
        <Button type="primary" onClick={handleAddPaper}>添加论文</Button>
      </Space>
      <Table
        columns={columns}
        rowKey="_id"
        dataSource={papers}
        loading={loading}
        pagination={pagination}
        onChange={handleTableChange}
        scroll={{ x: true }}
      />
    </div>
  );
};

export default PapersPage;