// Axios class to interact with the Room API
import axios from '../../axiosConfig';
import TokenManager from './TokenManager';

class RoomApi {
  constructor() {
    const token = TokenManager.getAccessToken();
    this.apiClient = axios.create({
      baseURL: axios.defaults.baseURL + '/rooms',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });
  }

  // Create a new room
  async createRoom(roomData) {
    try {
      const response = await this.apiClient.post('', roomData);
      return response.data;
    } catch (error) {
      console.error('Error creating room:', error);
      throw error;
    }
  }

  // Get all rooms
  async getAllRooms() {
    try {
      const response = await this.apiClient.get();
      return response.data;
    } catch (error) {
      console.error('Error fetching all rooms:', error);
      throw error;
    }
  }

  // Get room by ID
  async getRoomById(id) {
    try {
      const response = await this.apiClient.get(`/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching room by ID:', error);
      throw error;
    }
  }

  // Update room by ID
  async updateRoom(id, roomData) {
    try {
      const response = await this.apiClient.put(`/${id}`, roomData);
      return response.data;
    } catch (error) {
      console.error('Error updating room:', error);
      throw error;
    }
  }

  // Delete room by ID
  async deleteRoom(id) {
    try {
      await this.apiClient.delete(`/${id}`);
    } catch (error) {
      console.error('Error deleting room:', error);
      throw error;
    }
  }
}

export default new RoomApi();
