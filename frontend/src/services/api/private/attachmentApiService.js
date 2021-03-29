import axiosConfig from '../../../config/axiosConfig';

const ATTACHMENT_API_BASE_URL = '/auth/v1/attachment';

export const listAttachments = () => axiosConfig.instance
  .get(ATTACHMENT_API_BASE_URL)
  .then((response) => response.data);

export const getAttachmentByFilename = (filename) => axiosConfig.instance
  .get(`${ATTACHMENT_API_BASE_URL}/filename/${filename}`)
  .then((response) => response.data);

export const addAttachment = (file) => {
  const formData = new FormData();
  formData.append('file', file);
  return axiosConfig.instance
    .post(ATTACHMENT_API_BASE_URL, formData)
    .then((response) => response.data);
};

export const deleteAttachmentByUrl = (url) => {
  const encodedUrl = encodeURI(url);
  const filename = encodedUrl.replace(/(https:\/\/schulatlas.s3.amazonaws.com\/)/i, '');
  axiosConfig.instance
    .delete(`${ATTACHMENT_API_BASE_URL}/filename/${filename}`);
};
