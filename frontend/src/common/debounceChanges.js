const debounce = (callback, timer, setTimer) => {
  if (!timer) {
    const currentTimer = setTimeout(() => {
      callback();
      setTimer('');
    }, 1000);
    setTimer(currentTimer);
  }
};

export default debounce;
