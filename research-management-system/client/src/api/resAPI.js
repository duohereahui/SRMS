import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/system/research',
  timeout: 5000
});

// 获取所有科研成果
export const getAllResearch = async () => {
  try {
    const response = await api.get('/');
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取科研成果列表失败';
  }
};

// 按ID查询科研成果
export const getResearchById = async (researchId) => {
  try {
    const response = await api.get(`/${researchId}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取科研成果详情失败';
  }
};

// 按类型筛选（paper/patent）
export const getResearchByType = async (type) => {
  try {
    const response = await api.get('/type', { params: { type } });
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '按类型筛选失败';
  }
};

// 按状态筛选（draft/published/submitted）
export const getResearchByStatus = async (status) => {
  try {
    const response = await api.get('/state', { params: { status } });
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '按状态筛选失败';
  }
};

// 关键词搜索
export const searchResearch = async (keyword) => {
  try {
    const response = await api.get('/search', { params: { keyword } });
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '科研成果搜索失败';
  }
};

// 创建科研成果
export const createResearch = async (researchData) => {
  try {
    const response = await api.post('/', researchData);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '创建科研成果失败';
  }
};

// 更新科研成果
export const updateResearch = async (resId, updateData) => {
  try {
    const response = await api.put(`/${resId}`, updateData);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '更新科研成果失败';
  }
};

// 删除科研成果
export const deleteResearch = async (resId) => {
  try {
    await api.delete(`/delete/${resId}`);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '删除科研成果失败';
  }
};