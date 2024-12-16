import { jwtDecode } from 'jwt-decode';

const TokenManager = {
    getAccessToken: () => sessionStorage.getItem("accessToken"),

    getClaims: () => {
        const claims = sessionStorage.getItem("claims");
        return claims ? JSON.parse(claims) : undefined;
    },

    setAccessToken: (token) => {
        try {
            console.log("Setting token:", token); // Log the token being set
            const claims = jwtDecode(token); // Decodes JWT token to extract claims
            console.log("Decoded claims:", claims); // Log decoded claims

            sessionStorage.setItem("acessToken", token);
            sessionStorage.setItem("claims", JSON.stringify(claims));
            return claims; // Return decoded claims for further use
        } catch (error) {
            console.error("Invalid token", error);
            TokenManager.clear(); // Remove token and claims if decoding fails
            return undefined;
        }
    },

    getUserId: () => {
        const claims = TokenManager.getClaims();
        return claims?.userId || null;
    },

    getUserRole: () => {
        const claims = TokenManager.getClaims();
        return claims?.role || null;
    },

    isTokenExpired: () => {
        const claims = TokenManager.getClaims();
        if (!claims || !claims.exp) return true; // If no expiration in claims, treat as expired
        return Date.now() >= claims.exp * 1000; // Check if current time exceeds expiration
    },

    clear: () => {
        sessionStorage.removeItem("accessToken");
        sessionStorage.removeItem("claims");
    }
};

export default TokenManager;
