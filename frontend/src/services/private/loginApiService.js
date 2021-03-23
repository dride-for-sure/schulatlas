import axios from 'axios';

const AUTH_API_BASE_URL = '/auth/v1/login';

const login = (username, password) => axios
  .post(AUTH_API_BASE_URL, { username, password })
  .then((response) => response.data);

export default login;
