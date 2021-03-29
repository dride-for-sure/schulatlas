import axiosConfig from '../../../config/axiosConfig';

const COMPONENT_API_BASE_URL = '/auth/v1/component';

const listComponents = () => axiosConfig.instance
  .get(COMPONENT_API_BASE_URL)
  .then((response) => response.data);

export default listComponents;
