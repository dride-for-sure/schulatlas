import axiosConfig from '../../config/axiosConfig';

const ATTACHMENT_API_BASE_URL = '/auth/v1/attachment';

export const listAttachments = () => axiosConfig.instance
  .get(ATTACHMENT_API_BASE_URL)
  .then((response) => response.data);

export const getAttachmentByFilename = (filename) => axiosConfig.instance
  .get(`${ATTACHMENT_API_BASE_URL}/filename/${filename}`)
  .then((response) => response.data);

export const addAttachment = (file) => axiosConfig.instance
  .post(ATTACHMENT_API_BASE_URL, file)
  .then((response) => response.data);

export const deleteAttachmentByFilename = (filename) => axiosConfig.instance
  .delete(`${ATTACHMENT_API_BASE_URL}/filename/${filename}`);
