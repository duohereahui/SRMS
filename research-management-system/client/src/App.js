import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { Layout } from 'antd';
import { Helmet } from 'react-helmet';  // 新增导入
import HomePage from './pages/HomePage';
import PapersPage from './pages/PapersPage';
import NavBar from './components/NavBar';

const { Content, Footer } = Layout;

function App() {
  return (
    <Router>
      {/* 新增Helmet标签 */}
      <Helmet>
        <title>学院论文管理系统</title>
        <meta name="description" content="学院科研成果管理平台" />
      </Helmet>
      
      <Layout style={{ minHeight: '100vh' }}>
        <NavBar />
        <Content style={{ padding: '0 50px', marginTop: 64 }}>
          <div style={{ background: '#fff', padding: 24, minHeight: 380 }}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/papers" element={<PapersPage />} />
              <Route path="*" element={<Navigate to="/" />} />
            </Routes>
          </div>
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          Research Management System ©2023
        </Footer>
      </Layout>
    </Router>
  );
}

export default App;