import axiosConfig from '../config/axiosConfig';

/* SCHOOL ENDPOINTS */
export const listSchools = () => axiosConfig.instance
  .get('/auth/school')
  .then((response) => response.data);

export const getSchool = (number) => axiosConfig.instance
  .get(`/auth/school/${number}`)
  .then((response) => response.data);

export const addSchool = (school) => axiosConfig.instance
  .post('/auth/school', school)
  .then((response) => response.data);

export const updateSchool = (school) => axiosConfig.instance
  .put(`/auth/school/${school.number}`, school)
  .then((response) => response.data);

export const deleteSchool = (number) => axiosConfig.instance
  .delete(`/auth/school/${number}`);

/* PAGE ENDPOINTS */
export const listPages = () => axiosConfig.instance
  .get('/auth/page')
  .then((response) => response.data);

export const getPage = (name) => axiosConfig.instance
  .get(`/auth/page/${name}`)
  .then((response) => response.data);

export const addPage = (page) => axiosConfig.instance
  .post('/auth/page', page)
  .then((response) => response.data);

export const updatePage = (page) => axiosConfig.instance
  .put(`/auth/page/${page.name}`, page)
  .then((response) => response.data);

export const deletePage = (name) => axiosConfig.instance
  .delete(`/auth/page/${name}`);

/* LANDING ENDPOINTS */
export const getLandingPage = () => axiosConfig.instance
  .get('/auth/landingpage/')
  .then((response) => response.data);

export const markPageAsLandingPage = (name) => axiosConfig.instance
  .put(`/auth/landingpage/${name}`)
  .then((response) => response.data);

/* PROPERTY ENDPOINTS */
export const listPropertes = () => axiosConfig.instance
  .get('/auth/property')
  .then((response) => response.data);

export const addProperty = (property) => axiosConfig.instance
  .post('/auth/property', property)
  .then((response) => response.data);

export const updateProperty = (property) => axiosConfig.instance
  .put(`/auth/property/${property.name}`, property)
  .then((response) => response.data);

export const deleteProperty = (name) => axiosConfig.instance
  .delete(`/auth/property/${name}`);

/* COMPONENT ENDPOINTS */
export const listComponents = () => axiosConfig.instance
  .get('/auth/component')
  .then((response) => response.data);

/* ATTACHMENT ENDPOINTS */
export const listAttachments = () => axiosConfig.instance
  .get('/auth/attachment')
  .then((response) => response.data);

export const getAttachment = (fileName) => axiosConfig.instance
  .get(`/auth/attachment/${fileName}`)
  .then((response) => response.data);

export const addAttachment = (file) => axiosConfig.instance
  .post('/auth/attachment', file)
  .then((response) => response.data);

export const deleteAttachment = (fileName) => axiosConfig.instance
  .delete(`/auth/attachment/${fileName}`);
