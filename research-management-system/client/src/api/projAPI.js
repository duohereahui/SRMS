import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/system/project',
  timeout: 5000
});

// 获取所有项目
export const getAllProjects = async () => {
  try {
    const response = await api.get('/');
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取项目列表失败';
  }
};

// 按ID查询项目
export const getProjectById = async (partId) => {
  try {
    const response = await api.get(`/${partId}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取项目详情失败';
  }
};

// 项目搜索
export const searchProjects = async (keyword) => {
  try {
    const response = await api.get('/search', { params: { keyword } });
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '项目搜索失败';
  }
};

// 按部门查询项目
export const getProjectsByDept = async (deptId) => {
  try {
    const response = await api.get(`/department/${deptId}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取部门项目失败';
  }
};

// 按状态筛选项目
export const getProjectsByStatus = async (status) => {
  try {
    const response = await api.get(`/status/${status}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '按状态筛选失败';
  }
};

// 创建项目
export const createProject = async (projectData) => {
  try {
    const response = await api.post('/', projectData);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '创建项目失败';
  }
};

// 更新项目
export const updateProject = async (projectId, updateData) => {
  try {
    const response = await api.put(`/${projectId}`, updateData);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '更新项目失败';
  }
};

// 删除项目
export const deleteProject = async (projectId) => {
  try {
    await api.delete(`/${projectId}`);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '删除项目失败';
  }
};