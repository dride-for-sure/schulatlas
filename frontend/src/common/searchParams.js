const getSearchParams = (search) => ({
  page: parseInt(new URLSearchParams(search).get('page'), 10) || 0,
  size: parseInt(new URLSearchParams(search).get('size'), 10) || 3,
  sort: new URLSearchParams(search).get('sort') || '',
  direction: new URLSearchParams(search).get('direction') || 'asc',
});

export default getSearchParams;
