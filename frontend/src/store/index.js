import { create } from 'zustand';

const useUserStore = create((set) => ({
    userData: null,
    setUserData: () => set((userData) => ({ userData: userData })),
}))

export default useUserStore;