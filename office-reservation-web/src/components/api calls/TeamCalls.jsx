// Axios class to interact with the Team API
import axios from '../../axiosConfig';

class TeamApi {
  constructor() {
    this.apiClient = axios.create({
      baseURL: axios.defaults.baseURL + '/teams',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  // Create a new team
  async createTeam(teamData) {
    try {
      const response = await this.apiClient.post('/', teamData);
      return response.data;
    } catch (error) {
      console.error('Error creating team:', error);
      throw error;
    }
  }

  // Get all teams
  async getAllTeams() {
    try {
      const response = await this.apiClient.get('/');
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
    try {
      const response = await this.apiClient.put(`/${id}`, teamData);
      return response.data;
    } catch (error) {
      console.error('Error updating team:', error);
      throw error;
    }
  }

  // Delete team by ID
  async deleteTeam(id) {
    try {
      await this.apiClient.delete(`/${id}`);
    } catch (error) {
      console.error('Error deleting team:', error);
      throw error;
    }
  }
}

export default new TeamApi();
