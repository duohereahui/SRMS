import React from 'react';
import { Link } from 'react-router-dom';
import { Menu } from 'antd';

const NavBar = () => {
  return (
    <Menu mode="horizontal" theme="dark">
      <Menu.Item key="home">
        <Link to="/">首页</Link>
      </Menu.Item>
      <Menu.Item key="papers">
        <Link to="/papers">论文管理</Link>
      </Menu.Item>
    </Menu>
  );
};

export default NavBar;