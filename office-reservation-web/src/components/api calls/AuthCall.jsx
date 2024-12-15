// AuthCall.jsx
import axios from '../../axiosConfig';

class AuthCall {
  constructor() {
    this.apiClient = axios.create({
      baseURL: axios.defaults.baseURL + '/tokens',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  // Sign in and retrieve token
  async signIn(credentials) {
    try {
      const response = await this.apiClient.post('', credentials);
      return response.data; // Return the token
    } catch (error) {
      console.error('Error during sign-in:', error);
      throw error;
    }
  }
}

export default new AuthCall();
