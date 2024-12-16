import axios from '../../axiosConfig';
import TokenManager from './TokenManager';
class ReservationApi {
    constructor() {
        const token = TokenManager.getAccessToken();
        this.apiClient = axios.create({
            baseURL: axios.defaults.baseURL + '/reservations',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
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
            const response = await this.apiClient.get('', {
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

    // Get all reservations for the week starting from a specific date
    async getAllReservationsWeekly(startDate) {
      console.log('response', startDate);
        try {
            const response = await this.apiClient.get('/weekly', {
                params: { date: startDate },
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching weekly reservations:', error);
            throw error;
        }
    }
}

export default new ReservationApi();
