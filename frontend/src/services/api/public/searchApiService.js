import axios from 'axios';

const SCHOOL_API_BASE_URL = '/api/v1/search/';

export const searchForSchools = (searchString, queryString) => axios
  .get(`${SCHOOL_API_BASE_URL}school/${searchString}${queryString || ''}`)
  .then((response) => response.data);

export const searchForTypes = (searchString, queryString) => axios
  .get(`${SCHOOL_API_BASE_URL}type/${searchString}${queryString || ''}`)
  .then((response) => response.data);
