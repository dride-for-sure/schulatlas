import axios from 'axios';

const PAGE_API_BASE_URL = '/api/v1/page';

export const getPageBySlug = (slug) => axios
  .get(`${PAGE_API_BASE_URL}/slug/${slug}`)
  .then((response) => response.data);

export const getLandingPage = () => axios
  .get(`${PAGE_API_BASE_URL}/landingpage`)
  .then((response) => response.data);
