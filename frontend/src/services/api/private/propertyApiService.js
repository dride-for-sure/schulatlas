import axiosConfig from '../../../config/axiosConfig';

const PROPS_API_BASE_URL = '/auth/v1/property';

export const listPropertes = () => axiosConfig.instance
  .get(PROPS_API_BASE_URL)
  .then((response) => response.data);

export const addProperty = (property) => axiosConfig.instance
  .post(PROPS_API_BASE_URL, property)
  .then((response) => response.data);

export const updateProperty = (property) => axiosConfig.instance
  .put(`${PROPS_API_BASE_URL}/name/${property.name}`, property)
  .then((response) => response.data);

export const deletePropertyByName = (name) => axiosConfig.instance
  .delete(`${PROPS_API_BASE_URL}/name/${name}`);
