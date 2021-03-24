import axios from 'axios';

const PAGE_API_BASE_URL = '/api/v1/page';

const getPageByName = (name) => axios
  .get(`${PAGE_API_BASE_URL}/name/${name}`)
  .then((response) => response.data);

export default getPageByName;
