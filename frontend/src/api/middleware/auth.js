const fetchWrapper = async (url, options = {}) => {
    const token = localStorage.getItem('access_token');
    if (!token) throw new Error("No auth token set in local storage.");

    const response = await fetch(url, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
            ...options.headers,
        },
    });

    let jsonResponse;
    try {
        jsonResponse = await response.json();
    } catch (error) {
        console.error("Failed to parse JSON response:", error);
        throw new Error("Failed to parse JSON response");
    }

    if (!response.ok) {
        console.error("Response error:", jsonResponse.message);
        throw new Error(jsonResponse.message || "Error in response");
    }

    return jsonResponse;
};

export default fetchWrapper;
