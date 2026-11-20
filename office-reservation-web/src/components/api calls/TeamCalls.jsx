// Axios class to interact with the Team API
import axios from '../../axiosConfig';
import TokenManager from './TokenManager';

class TeamApi {
  constructor() {
    const token = TokenManager.getAccessToken();
    this.apiClient = axios.create({
      baseURL: axios.defaults.baseURL + 'teams',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });
  }

  // Create a new team
  async createTeam(teamData) {
    try {
      const response = await this.apiClient.post('', teamData);
      return response.data;
    } catch (error) {
      console.error('Error creating team:', error);
      throw error;
    }
  }

  async getAllTeams() {
    try {
      const response = await this.apiClient.get();
      console.log("Fetched teams:", response.data);
      return response.data;
    } catch (error) {
      console.error('Error fetching all teams:', error);
      throw error;
    }
  }
  
  // Get team by ID
  async getTeamById(id) {
    try {
      const response = await this.apiClient.get(`/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching team by ID:', error);
      throw error;
    }
  }

  // Get teams by user ID
  async getTeamsByUserId(userId) {
    try {
      const response = await this.apiClient.get(`/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching teams by user ID:', error);
      throw error;
    }
  }

  // Update team by ID
  async updateTeam(id, teamData) {
    console.log(`Sending PUT request to /teams/${id}`);
    console.log("Payload:", teamData);
    try {
      const response = await this.apiClient.put(`/${id}`, teamData);
      return response.data;
    } catch (error) {
      console.error("Error in updateTeam:", error.response || error);
      throw error;
    }
  }
  

  // Delete team by ID
  async deleteTeam(teamId) {
    try {
      const response = await this.apiClient.delete(`/${teamId}`);
      console.log("Team deleted successfully:", response.status); // Confirm deletion
    } catch (error) {
      if (error.response?.status === 404) {
        console.warn("Team not found (404). Ignoring since it might be already deleted.");
      } else {
        console.error("Error deleting team:", error);
        throw error;
      }
    }
  }
  
}

export default new TeamApi();
