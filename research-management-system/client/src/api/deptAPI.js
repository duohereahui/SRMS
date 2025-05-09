import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/system/department',
  timeout: 5000
});

// 获取所有学院
export const getAllDepts = async () => {
  try {
    const response = await api.get('/');
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取学院列表失败';
  }
};

// 获取学院教师
export const getDeptTeachers = async (deptId) => {
  try {
    const response = await api.get(`/${deptId}/teachers`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取教师列表失败';
  }
};

// 更新学院信息
export const updateDept = async (deptId, { name, description }) => {
  const params = new URLSearchParams();
  if (name) params.append('name', name);
  if (description) params.append('description', description);

  try {
    await api.put(`/${deptId}`, params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '更新学院信息失败';
  }
};

// 添加学院
export const addDept = async ({ name, description }) => {
  const params = new URLSearchParams();
  params.append('name', name);
  params.append('description', description);

  try {
    await api.post('/', params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '添加学院失败';
  }
};

// 删除学院
export const deleteDept = async (deptId) => {
  try {
    await api.delete('/', { params: { deptId } });
    return true;
  } catch (error) {
    throw error.response?.data?.message || '删除学院失败';
  }
};