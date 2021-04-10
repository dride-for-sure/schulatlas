import { func, object, string } from 'prop-types';
import { useEffect, useRef } from 'react';
import styled from 'styled-components/macro';
import { addSearchBarClickEvent, addSearchBarEnterEvent, removeSearchBarClickEvent, removeSearchBarEnterEvent } from '../../../events/searchBarEvents';
import Logo from '../../icons/Logo';
import Navigation from '../../navigation/cms/Navigation';
import PaddingContainerS from '../../padding/_PaddingContainerS';
import SearchBar from '../../search/cms/Searchbar';
import SearchResult from '../../search/cms/SearchResult';
import FlexRowCenter from '../../structures/_FlexRowCenter';
import MaxWidthL from '../../structures/_MaxWidthL';

export default function HeaderMaps({
  searchString,
  onSearch,
  schoolSearchResults,
  typeSearchResults,
  onSearchBarLeave,
  onSearchBarEnter,
  searchQueries }) {
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
      <Wrapper>
        <PaddingContainer>
          <MaxWidthContainer>
            <Logo />
            {onSearch && (
              <PositionRelative>
                <SearchBar
                  inputRef={searchBarRef}
                  searchString={searchString}
                  onSearch={onSearch}
                  onLeave={onSearchBarLeave}
                  onEnter={onSearchBarEnter} />
                <SearchResult
                  path="/maps"
                  searchQueries={searchQueries}
                  schoolSearchResults={schoolSearchResults}
                  typeSearchResults={typeSearchResults} />
              </PositionRelative>
            ) }
            <Navigation />
          </MaxWidthContainer>
        </PaddingContainer>
      </Wrapper>
    </>
  );
}

const Wrapper = styled.header`
  position: absolute;
  width: 100%;
`;

const PaddingContainer = styled.div`
  ${FlexRowCenter}
  ${PaddingContainerS}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL};
  display: grid;
  grid-template-columns: min-content 300px 1fr;
  grid-template-areas: "logo search navi";
  grid-gap: 0 var(--default-padding-m);
  
  > align-self {
    grid-area: logo;
  }

  > div {
    grid-area: search;
  }

  > ol {
    grid-area: navi;
  }

  > * {
    align-self:center;
  }

  * {
    min-width: 0;
  }
`;

const PositionRelative = styled.div`
  position: relative;
`;

HeaderMaps.propTypes = {
  searchString: string,
  onSearch: func,
  typeSearchResults: object,
  schoolSearchResults: object,
  onSearchBarLeave: func,
  onSearchBarEnter: func,
  searchQueries: string,
};
