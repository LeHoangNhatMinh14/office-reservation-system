// AuthCall.jsx
import axios from '../../axiosConfig';

const AuthCall = {
  // Sign in and retrieve token
  signIn: async (credentials) => {
    try {
      const response = await axios.post('/tokens', credentials, {
        headers: { 'Content-Type': 'application/json' },
      });
      return response.data; // Return the token
    } catch (error) {
      console.error('Error during sign-in:', error);
      throw error;
    }
  }
};

export default AuthCall;
