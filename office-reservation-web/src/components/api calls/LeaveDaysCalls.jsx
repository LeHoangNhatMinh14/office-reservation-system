import axios from '../../axiosConfig';
import TokenManager from './TokenManager';

class LeaveDaysApi {
    constructor() {
        const token = TokenManager.getAccessToken();
        this.apiClient = axios.create({
            baseURL: axios.defaults.baseURL + '/leave_days',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
        });
    }

    async createLeaveDays(leaveData){
        try {
            const response = await this.apiClient.post('', leaveData);
            return response.data;
        } catch (error) {
            console.error('Error creating leave days:', error);
            throw error;
        }
    }

    async getLeaveDaysByUserId(userId){
        try {
            const response = await this.apiClient.get(`/user/${userId}`, {
                params: { userId },
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching leave days by user ID:', error);
            throw error;
        }
    }

    async cancelLeaveDays(id){
        try {
            await this.apiClient.delete(`/${id}`);
        } catch (error) {
            console.error('Error canceling leave days:', error);
            throw error;
        }
    }

    async getLeaveDaysById (id){
        try {
            const response = await this.apiClient.get(`/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching leave days by ID:', error);
            throw error;
        }
    }
}

export default new LeaveDaysApi();