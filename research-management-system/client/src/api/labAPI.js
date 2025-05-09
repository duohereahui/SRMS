import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/system/lab',
  timeout: 5000
});

// 创建新的实验室
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

// 更新实验室信息
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

// 删除实验室
export const deleteLab = async (labId) => {
  try {
    await api.delete(`/${labId}`);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '删除实验室失败';
  }
};

// keyword实验室模糊搜索（范围name+description）
export const searchLabs = async (keyword) => {
  try {
    const response = await api.get('/search', { params: { name: keyword } });
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '搜索实验室失败';
  }
};

// 获取单个实验室详情
export const getLabDetails = async (labId) => {
  try {
    const response = await api.get(`/${labId}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室详情失败';
  }
};

// 获取所有实验室
export const getAllLabs = async () => {
  try {
    const response = await api.get('/');
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室列表失败';
  }
};

// 获取实验室学生
export const getLabStudents = async (labId) => {
  try {
    const response = await api.get(`/${labId}/students`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室学生失败';
  }
};

//获取实验室教师
export const getLabTeachers = async (labId) => {
  try {
    const response = await api.get(`/${labId}/teachers`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室教师失败';
  }
};

// 获取实验室全部资源
export const getLabResources = async (labId) => {
  try {
    const response = await api.get(`/${labId}/resources`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取实验室资源失败';
  }
};

//为指定实验室新增资源
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