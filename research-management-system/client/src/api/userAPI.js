import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/system/user',
  timeout: 5000
});

// 登录接口
export const login = async (credential, password) => {
  const params = new URLSearchParams();
  params.append('credential', credential);
  params.append('password', password);
  
  try {
    const response = await api.post('/login', params);
    return response.data.data; // 返回token
  } catch (error) {
    throw error.response?.data?.message || '登录失败';
  }
};

//用户注册
// /**
//  * @param {Object} userData - 用户注册信息
//  * @param {string} userData.username - 用户名
//  * @param {string} userData.password - 密码
//  * @param {string} userData.email - 邮箱
//  * @param {string} userData.phone - 手机号
//  * @param {string} userData.role - 角色（student/teacher）
//  * @param {string} userData.department_id - 院系ID（仅对于学生）
//  * @param {string} userData.lab_id - 实验室ID
//  * @param {string} userData.teacher_id - 教师ID（仅对于学生）
//  * @param {string} userData.description - 描述(没有就传入空值)
//  * @returns {Promise<boolean>} - 注册成功返回true
//  * @throws {string} - 注册失败抛出错误信息
// */
export const register = async (userData) => {
  const params = new URLSearchParams();
  Object.entries(userData).forEach(([key, value]) => {
    params.append(key, value);
  });

  try {
    await api.post('/register', params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '注册失败';
  }
};

// 更新用户信息
export const updateUser = async (userId, updateFields) => {
  const params = new URLSearchParams();
  Object.entries(updateFields).forEach(([key, value]) => {
    if (value) params.append(key, value);
  });

  try {
    await api.put(`/${userId}`, params);
    return true;
  } catch (error) {
    throw error.response?.data?.message || '更新失败';
  }
};

// 获取用户详细信息
export const getUserDetails = async (userId) => {
  try {
    const response = await api.get(`/${userId}`);
    return response.data.data;
  } catch (error) {
    throw error.response?.data?.message || '获取用户信息失败';
  }
};

// 删除用户
export const deleteUser = async (userId) => {
  try {
    await api.delete('/delete', { params: { user_id: userId } });
    return true;
  } catch (error) {
    throw error.response?.data?.message || '删除失败';
  }
};