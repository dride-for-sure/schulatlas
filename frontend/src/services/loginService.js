import axios from 'axios';

const login = (username, password) => axios
  .post('/authenticate', { username, password })
  .then((response) => response.data);

export default login;
