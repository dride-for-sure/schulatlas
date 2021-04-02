export const getSumOfTypes = (types) =>
  types.reduce((sum, type) => sum + type.count, 0);

export const removeTypeless = (types) =>
  types.filter(((type) => type.name !== ''));
