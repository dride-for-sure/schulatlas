const removeUsedProperties = (available, used) => {
  if (used !== null) {
    return available.filter((availableProp) =>
      !used.some((usedProp) => usedProp.name === availableProp.name));
  }
  return available;
};

export default removeUsedProperties;
