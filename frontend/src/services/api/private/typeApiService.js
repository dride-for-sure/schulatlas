import axiosConfig from '../../../config/axiosConfig';

const SCHOOL_API_BASE_URL = '/auth/v1/type';

export const listUsedTypes = () => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/used`)
  .then((response) => response.data);

export const listAvailableTypes = () => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/available`)
  .then((response) => response.data);
