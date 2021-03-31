import { v4 as uuid } from 'uuid';

export const addIndizesToNestedData = (data, idChain) => {
  const id = idChain ? `${idChain}_${uuid()}` : uuid();
  let indexedData = data;
  if ((Object.prototype.toString.call(indexedData) === '[object Object]') && indexedData !== null) {
    indexedData = { ...indexedData, id };
    Object.keys(indexedData).forEach((key) => {
      indexedData[key] = addIndizesToNestedData(indexedData[key], id);
    });
  }
  if (Array.isArray(indexedData) && indexedData.length) {
    return indexedData.map((array) => addIndizesToNestedData(array, id));
  }
  return indexedData;
};

export const updateNestedData = (data, id, entry) => {
  let updatedData = data;
  if ((Object.prototype.toString.call(updatedData) === '[object Object]') && updatedData !== null) {
    if (updatedData.id === id) {
      updatedData = { ...updatedData, ...entry };
    } else if (id.startsWith(updatedData.id)) {
      Object.keys(updatedData).forEach((key) => {
        updatedData[key] = updateNestedData(updatedData[key], id, entry);
      });
    }
  }
  if (Array.isArray(updatedData) && updatedData.length) {
    return updatedData.map((array) => updateNestedData(array, id, entry));
  }
  return updatedData;
};
