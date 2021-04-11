import axios from 'axios';
import { getQueryStringForBounds } from '../../../common/searchParams';

const SCHOOL_API_BASE_URL = '/api/v1/maps';

export const searchMapByBounds = (bounds) => axios
  .get(`${SCHOOL_API_BASE_URL}${getQueryStringForBounds(bounds)}`)
  .then((response) => response.data);

export const searchMapByBoundsAndType = (type, bounds) => axios
  .get(`${SCHOOL_API_BASE_URL}/type/${type}${getQueryStringForBounds(bounds)}`)
  .then((response) => response.data);

export const searchMapByString = (searchString, bounds) => axios
  .get(`${SCHOOL_API_BASE_URL}/search/${searchString}${getQueryStringForBounds(bounds)}`)
  .then((response) => response.data);
