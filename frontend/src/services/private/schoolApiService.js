import axiosConfig from '../../config/axiosConfig';

const SCHOOL_API_BASE_URL = '/auth/v1/school';

export const listSchools = () => axiosConfig.instance
  .get(SCHOOL_API_BASE_URL)
  .then((response) => response.data);

export const listTypes = () => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/type`)
  .then((response) => response.data);

export const getSchoolByNumber = (number) => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/number/${number}`)
  .then((response) => response.data);

export const getSchoolByType = (type) => axiosConfig.instance
  .get(`${SCHOOL_API_BASE_URL}/type/${type}`)
  .then((response) => response.data);

export const addSchool = (school) => axiosConfig.instance
  .post(SCHOOL_API_BASE_URL, school)
  .then((response) => response.data);

export const updateSchool = (school) => axiosConfig.instance
  .put(`${SCHOOL_API_BASE_URL}/number/${school.number}`, school)
  .then((response) => response.data);

export const deleteSchoolByNumber = (number) => axiosConfig.instance
  .delete(`${SCHOOL_API_BASE_URL}/number/${number}`);
