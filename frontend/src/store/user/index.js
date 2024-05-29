import fetchWrapper from "../../api/middleware/auth";
import {create} from "zustand";

export const useUserStore = create((set) => ({
    userData: null,
    getUserData: async () => {
        try {
            const data = await fetchWrapper('http://localhost:8080/api/v1/med-ass/access', {
                method: 'GET',
            });
            set({ userData: data });
            return data
        } catch (error) {
            console.error("Failed to fetch user data:", error);
        }
    },
    setUserData: (userData) => set({ userData: userData }),
}));