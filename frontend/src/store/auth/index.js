import {create} from "zustand";

export const useAuthStore = create((set) => (
    {
        token: null,
        isAuth: false,
        setToken: (token) => set({token}),
        login: async (email, password) => {
            const response = await fetch('http://localhost:8080/api/v1/med-ass/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({email, password})
            })
            if (response.ok) {
                const data = await response.json()
                console.log(data)
                localStorage.setItem('access_token', data.access_token)
                set({token: data.access_token, isAuth: true})
                if (data.error) {
                    set({isAuth: false})
                }
                return await data;
            } else {
                set({isAuth: false})
                return null;
            }
        },
        removeToken: () => set({token: null})
    }
))