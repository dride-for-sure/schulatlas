import axiosConfig from '../../../config/axiosConfig';

const PAGE_API_BASE_URL = '/auth/v1/page';

export const listPages = () => axiosConfig.instance
  .get(PAGE_API_BASE_URL)
  .then((response) => response.data);

export const getPageBySlug = (slug) => axiosConfig.instance
  .get(`${PAGE_API_BASE_URL}/slug/${slug}`)
  .then((response) => response.data);

export const addPage = (page) => axiosConfig.instance
  .post(PAGE_API_BASE_URL, page)
  .then((response) => response.data);

export const updatePage = (page) => axiosConfig.instance
  .put(`${PAGE_API_BASE_URL}/slug/${page.slug}`, page)
  .then((response) => response.data);

export const deletePageBySlug = (slug) => axiosConfig.instance
  .delete(`${PAGE_API_BASE_URL}/slug/${slug}`);

export const setLandingPageBySlug = (slug) => axiosConfig.instance
  .put(`${PAGE_API_BASE_URL}/slug/${slug}/landingpage`)
  .then((response) => response.data);
