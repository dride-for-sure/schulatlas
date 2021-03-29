const getSumOfTypes = (types) => types.reduce((sum, type) => sum + type.count, 0);

export default getSumOfTypes;
