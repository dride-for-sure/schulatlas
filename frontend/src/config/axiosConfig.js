import axios from 'axios';

const instance = axios.create();

const setAuthToken = (token) => {
  instance.defaults.headers.common.Authorization = `Bearer ${token}`;
};

export default { instance, setAuthToken };
