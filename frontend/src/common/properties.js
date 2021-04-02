const removeUsedProperties = (available, used) => {
  if (used) {
    return available.filter((availableProp) =>
      !used.some((usedProp) => usedProp.name === availableProp.name));
  }
  return available;
};

export default removeUsedProperties;
