import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/system/lab',
  timeout: 5000
});

// 实验室基础操作
export const createLab = async (labData) => {
  const params = new URLSearchParams();
  params.append('name', labData.name);
  params.append('department_id', labData.department_id);
  params.append('location', labData.location);
  params.append('description', labData.description);

  try {
    await api.post('/', params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '创建实验室失败';
  }
};

export const updateLab = async (labId, updateData) => {
  const params = new URLSearchParams();
  if (updateData.description) params.append('description', updateData.description);
  if (updateData.location) params.append('location', updateData.location);
  if (updateData.department_id) params.append('department_id', updateData.department_id);

  try {
    await api.put(`/${labId}`, params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '更新实验室失败';
  }
};

export const deleteLab = async (labId) => {
  try {
    await api.delete(`/${labId}`);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '删除实验室失败';
  }
};

// 实验室查询
export const searchLabs = async (keyword) => {
  try {
    const response = await api.get('/search', { params: { name: keyword } });
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '搜索实验室失败';
  }
};

export const getLabDetails = async (labId) => {
  try {
    const response = await api.get(`/${labId}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室详情失败';
  }
};

export const getAllLabs = async () => {
  try {
    const response = await api.get('/');
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室列表失败';
  }
};

// 实验室成员管理
export const getLabStudents = async (labId) => {
  try {
    const response = await api.get(`/${labId}/students`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室学生失败';
  }
};

export const getLabTeachers = async (labId) => {
  try {
    const response = await api.get(`/${labId}/teachers`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室教师失败';
  }
};

// 实验室资源管理
export const getLabResources = async (labId) => {
  try {
    const response = await api.get(`/${labId}/resources`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室资源失败';
  }
};

export const createResource = async (labId, resourceData) => {
  const params = new URLSearchParams();
  params.append('name', resourceData.name);
  params.append('location', resourceData.location);
  params.append('type', resourceData.type);
  params.append('purchase_date', resourceData.purchase_date);

  try {
    await api.post(`/${labId}/resources`, params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '创建资源失败';
  }
};