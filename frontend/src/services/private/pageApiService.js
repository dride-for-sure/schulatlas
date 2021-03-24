import axiosConfig from '../../config/axiosConfig';

const PAGE_API_BASE_URL = '/auth/v1/page';

export const listPages = () => axiosConfig.instance
  .get(PAGE_API_BASE_URL)
  .then((response) => response.data);

export const getPageByName = (name) => axiosConfig.instance
  .get(`${PAGE_API_BASE_URL}/name/${name}`)
  .then((response) => response.data);

export const addPage = (page) => axiosConfig.instance
  .post(PAGE_API_BASE_URL, page)
  .then((response) => response.data);

export const updatePage = (page) => axiosConfig.instance
  .put(`${PAGE_API_BASE_URL}/name/${page.name}`, page)
  .then((response) => response.data);

export const deletePageByName = (name) => axiosConfig.instance
  .delete(`${PAGE_API_BASE_URL}/name/${name}`);

export const setLandingPageByName = (name) => axiosConfig.instance
  .put(`${PAGE_API_BASE_URL}/name/${name}/landingpage`)
  .then((response) => response.data);
