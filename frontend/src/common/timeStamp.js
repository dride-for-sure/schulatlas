const convertTimeStampToDate = (uts) => {
  const timestamp = new Date(uts);
  return `${timestamp.getFullYear()}-${timestamp.getMonth() + 1}-${timestamp.getDate()}`;
};

export default convertTimeStampToDate;
