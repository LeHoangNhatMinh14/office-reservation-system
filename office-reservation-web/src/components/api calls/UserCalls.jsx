// Axios class to interact with the User API
import axios from '../../axiosConfig';

class UserApi {
  constructor() {
    this.apiClient = axios.create({
      baseURL: axios.defaults.baseURL + '/users',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  // Create a new user
  async createUser(userData) {
    try {
      const response = await this.apiClient.post('', userData);
      return response.data;
    } catch (error) {
      console.error('Error creating user:', error);
      throw error;
    }
  }

  // Get all users
  async getAllUsers() {
    try {
      const response = await this.apiClient.get();
      return response.data;
    } catch (error) {
      console.error('Error fetching all users:', error);
      throw error;
    }
  }

  // Get user by ID
  async getUserById(id) {
    try {
      const response = await this.apiClient.get(`/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching user by ID:', error);
      throw error;
    }
  }

  // Update user by ID
  async updateUser(id, userData) {
    try {
      const response = await this.apiClient.put(`/${id}`, userData);
      return response.data;
    } catch (error) {
      console.error('Error updating user:', error);
      throw error;
    }
  }

  // Delete user by ID
  async deleteUser(id) {
    try {
      await this.apiClient.delete(`/${id}`);
    } catch (error) {
      console.error('Error deleting user:', error);
      throw error;
    }
  }
}

export default new UserApi();
