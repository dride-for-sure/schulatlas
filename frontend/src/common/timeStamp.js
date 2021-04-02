const convertTimeStampToDate = (uts) => {
  const timestamp = new Date(uts);
  const getMonth = (`0${timestamp.getMonth()}`).slice(-2);
  const getDate = (`0${timestamp.getDate()}`).slice(-2);
  return `${timestamp.getFullYear()}-${getMonth}-${getDate}`;
};

export default convertTimeStampToDate;
