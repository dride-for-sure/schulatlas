import axios from 'axios';

const SCHOOL_API_BASE_URL = '/api/v1/school';

export const listSchools = (page = '', size = '', sort = '', direction = '') => axios
  .get(`${SCHOOL_API_BASE_URL}?page=${page}&size=${size}&sort=${sort}&direction=${direction}`)
  .then((response) => response.data);

export const getSchoolByNumber = (number) => axios
  .get(`${SCHOOL_API_BASE_URL}/number/${number}`)
  .then((response) => response.data);

export const markOutdatedByNumber = (number) => axios
  .post(`${SCHOOL_API_BASE_URL}/number/${number}/outdated`)
  .then((response) => response.data);
