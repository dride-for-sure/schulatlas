import { func, object, string } from 'prop-types';
import { useEffect, useRef } from 'react';
import styled from 'styled-components/macro';
import { addSearchBarClickEvent, addSearchBarEnterEvent, removeSearchBarClickEvent, removeSearchBarEnterEvent } from '../../../events/searchBarEvents';
import Image from '../../../resources/images/HeaderBackgroundSmall.jpg';
import BrandBar from '../../brandBar/BrandBar';
import Logo from '../../icons/Logo';
import Navigation from '../../navigation/cms/Navigation';
import SearchBar from '../../search/cms/Searchbar';
import SearchResult from '../../search/cms/SearchResult';
import PositionRelative from '../../structures/PositionRelative';
import FlexRowCenter from '../../structures/_FlexRowCenter';
import MaxWidth from '../../structures/_MaxWidth';

export default function HeaderWithSearch({
  searchString,
  onSearch,
  schoolSearchResults,
  typeSearchResults,
  onSearchBarLeave,
  onSearchBarEnter }) {
  const searchBarRef = useRef(null);

  useEffect(() => {
    if (searchBarRef.current) {
      addSearchBarClickEvent(searchBarRef, onSearchBarLeave);
      addSearchBarEnterEvent(searchBarRef, onSearchBarEnter);
    }
    return (() => {
      removeSearchBarClickEvent(searchBarRef, onSearchBarLeave);
      removeSearchBarEnterEvent(searchBarRef, onSearchBarEnter);
    });
  }, []);

  return (
    <>
      <Wrapper background={Image}>
        <Container>
          <Logo />
          <PositionRelative>
            <SearchBar
              inputRef={searchBarRef}
              searchString={searchString}
              onSearch={onSearch}
              onLeave={onSearchBarLeave}
              onEnter={onSearchBarEnter} />
            <SearchResult
              schoolSearchResults={schoolSearchResults}
              typeSearchResults={typeSearchResults} />
          </PositionRelative>
          <Navigation />
        </Container>
      </Wrapper>
      <BrandBar />
    </>
  );
}

const Wrapper = styled.header`
  background-image: url(${(props) => props.background});
  background-repeat: no-repeat;
  background-position: center;
  background-position: 50% 50%;
  background-size: cover;
  ${FlexRowCenter};
`;

const Container = styled.div`
  ${MaxWidth};
  display: grid;
  grid-template-columns: min-content 300px 1fr;
  grid-gap: 0 calc(var(--container-padding) * 2);
  padding: var(--container-padding) calc(var(--container-padding) * 2);

  > * {
    align-self:center;
  }

  * {
    min-width: 0;
  }
`;

HeaderWithSearch.propTypes = {
  searchString: string.isRequired,
  onSearch: func.isRequired,
  typeSearchResults: object,
  schoolSearchResults: object,
  onSearchBarLeave: func.isRequired,
  onSearchBarEnter: func.isRequired,
};
