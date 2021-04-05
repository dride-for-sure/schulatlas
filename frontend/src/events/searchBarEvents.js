const isLeavingSearchBar = (searchBarRef, event, onSearchBarLeave) => {
  if (event.target !== searchBarRef.current) {
    onSearchBarLeave();
  }
};

const isKeyDownEnter = (event, searchBarRef, onSearchBarEnter) => {
  if (event.target === searchBarRef.current && event.key === 'Enter') {
    onSearchBarEnter();
    document.activeElement.blur();
  }
};

export const addSearchBarClickEvent = (searchBarRef, onSearchBarLeave) => {
  document.addEventListener('click', (event) => isLeavingSearchBar(searchBarRef, event, onSearchBarLeave));
};

export const removeSearchBarClickEvent = (searchBarRef, onSearchBarLeave) => {
  document.removeEventListener('click', (event) => isLeavingSearchBar(searchBarRef, event, onSearchBarLeave));
};

export const addSearchBarEnterEvent = (searchBarRef, onSearchBarEnter) => {
  document.addEventListener('keydown', (event) => isKeyDownEnter(event, searchBarRef, onSearchBarEnter));
};

export const removeSearchBarEnterEvent = (searchBarRef, onSearchBarEnter) => {
  document.removeEventListener('keydown', (event) => isKeyDownEnter(event, searchBarRef, onSearchBarEnter));
};
