import axiosConfig from '../../../config/axiosConfig';

const SCHOOL_API_BASE_URL = '/auth/v1/school';

export const listSchools = (queryString) => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}${queryString}`)
  .then((response) => response.data);

export const getSchoolByNumber = (number) => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/number/${number}`)
  .then((response) => response.data);

export const getSchoolsByType = (type, queryString) => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/type/${type}${queryString}`)
  .then((response) => response.data);

export const addSchool = (school) => axiosConfig.instance
  .post(SCHOOL_API_BASE_URL, school)
  .then((response) => response.data);

export const updateSchool = (school) => axiosConfig.instance
  .put(`${SCHOOL_API_BASE_URL}/number/${school.number}`, school)
  .then((response) => response.data);

export const deleteSchoolByNumber = (number) => axiosConfig.instance
  .delete(`${SCHOOL_API_BASE_URL}/number/${number}`);
