// Axios class to interact with the Reservation API
import axios from '../../axiosConfig';

class ReservationApi {
  constructor() {
    this.apiClient = axios.create({
      baseURL: axios.defaults.baseURL + '/reservations',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  // Create a new reservation
  async createReservation(reservationData) {
    try {
      const response = await this.apiClient.post('', reservationData);
      return response.data;
    } catch (error) {
      console.error('Error creating reservation:', error);
      throw error;
    }
  }

  // Get reservations by room ID
  async getReservationsByRoomId(roomId) {
    try {
      const response = await this.apiClient.get('/', {
        params: { roomId },
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching reservations by room ID:', error);
      throw error;
    }
  }

  // Cancel a reservation by ID
  async cancelReservation(id) {
    try {
      await this.apiClient.delete(`/${id}`);
    } catch (error) {
      console.error('Error canceling reservation:', error);
      throw error;
    }
  }
}

export default new ReservationApi();
