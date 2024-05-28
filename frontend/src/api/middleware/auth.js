
const fetchWrapper = async (url, options = {}) => {
    const token = localStorage.getItem('access_token');

    const headers = {
        'Content-Type': 'application/json',
        ...options.headers,
    };

    if (token) {
        headers.Authorization = `Bearer ${token}`;
    } else {
        throw new Error("No auth token set in local storage.")
    }

    const response = await fetch(url, {
        ...options,
        headers,
    });

    if (!response.ok) {
        // Handle HTTP errors
        const error = await response.json();
        throw new Error(error.message);
    }

    return response.json();
};

export default fetchWrapper;