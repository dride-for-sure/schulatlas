import axios from 'axios';

/* SCHOOL ENDPOINTS */
export const listSchools = () => axios
  .get('/api/school')
  .then((response) => response.data);

export const getSchool = (number) => axios
  .get(`/api/school/${number}`)
  .then((response) => response.data);

export const markSchoolOutdated = (number) => axios
  .post(`/api/school/${number}/outdated`)
  .then((response) => response.data);

/* PAGE ENDPOINTS */
export const getPage = (name) => axios
  .get(`/api/page/${name}`)
  .then((response) => response.data);
