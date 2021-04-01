export const getSearchParams = (search) => ({
  page: parseInt(new URLSearchParams(search).get('page'), 10),
  size: parseInt(new URLSearchParams(search).get('size'), 10) || 3, // As long as there are not hundreds of schools...
  sort: new URLSearchParams(search).get('sort'),
  direction: new URLSearchParams(search).get('direction'),
});

export const getQueryStringForPaginate = (whichWay, search, totalPages) => {
  const params = getSearchParams(search);
  const getPage = () => {
    if (whichWay === 'last') {
      return (Object.is(params.page, NaN) || params.page <= 1)
        ? 1
        : params.page - 1;
    }
    if (Object.is(params.page, NaN)) {
      return 2;
    }
    if (params.page < totalPages) {
      return params.page + 1;
    }
    return 1;
  };
  const page = `?page=${getPage()}`;
  const size = params.size !== null ? `&size=${params.size}` : '';
  const sort = params.sort !== null ? `&sort=${params.sort}` : '';
  const direction = params.direction !== null ? `&direction=${params.direction}` : '';
  return `${page}${size}${sort}${direction}`;
};

export const getQueryStringForToggleSort = (sortBy, search) => {
  const params = getSearchParams(search);
  const page = Object.is(params.page, NaN) ? '?page=1' : `?page=${params.page}`;
  const size = params.size !== null && `&size=${params.size}`;
  const sort = `&sort=${sortBy}`;
  const getDirection = () => {
    if (params.direction === null || params.direction === 'asc') {
      return '&direction=desc';
    }
    return '&direction=asc';
  };
  return `${page}${size}${sort}${getDirection()}`;
};

export const getBackendQueryString = (search) => {
  const params = getSearchParams(search);
  const page = Object.is(params.page, NaN) ? '?page=0' : `?page=${params.page - 1}`;
  const size = params.size !== null ? `&size=${params.size}` : '';
  const sort = params.sort !== null ? `&sort=${params.sort}` : '';
  const direction = params.direction !== null ? `&direction=${params.direction}` : '';
  return `${page}${size}${sort}${direction}`;
};
