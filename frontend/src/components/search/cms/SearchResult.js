import { object } from 'prop-types';
import styled from 'styled-components/macro';
import Headline from '../../headlines/Headline';
import HeadlineWithSubtitle from '../../headlines/HeadlineWithSubtitle';
import RegularLink from '../../links/RegularLink';
import UnorderedList from '../../lists/_UnorderedList';

export default function SearchResult({ schoolSearchResults, typeSearchResults }) {
  if (!schoolSearchResults && !typeSearchResults) {
    return null;
  }

  const count = (schoolSearchResults ? schoolSearchResults.numberOfElements : 0)
  + (typeSearchResults ? typeSearchResults.numberOfElements : 0);

  if (count === 0) {
    return null;
  }

  return (
    <Container>
      <HeadlineWithSubtitle title="Results" subtitle={`Found: ${count}`} />
      {typeSearchResults && typeSearchResults.numberOfElements > 0 && (
        <>
          <Headline size="s">Types:</Headline>
          <List>
            {typeSearchResults.content.map((type) => (
              <li key={type.name}>
                <RegularLink to={`/cms/type/${type.name}`}>
                  {type.name}
                </RegularLink>
              </li>
            ))}
          </List>
        </>
      )}
      {schoolSearchResults && schoolSearchResults.numberOfElements > 0 && (
        <>
          <Headline size="s">Schools:</Headline>
          <List>
            {schoolSearchResults.content.map((school) => (
              <li key={school.number}>
                <RegularLink to={`/cms/school/${school.number}`}>
                  {`${school.name}, `}
                  <i>{school.address.city}</i>
                </RegularLink>
              </li>
            ))}
          </List>
        </>
      )}
    </Container>
  );
}

const Container = styled.div`
  position: absolute;
  top: 38px;
  border-radius: var(--border-radius);
  border: 1px solid rgb(250,250,250);
  width: 300px;
  box-sizing: border-box;
  padding: calc(var(--container-padding) * 0.5) var(--container-padding) calc(var(--container-padding) * 0.9);
  background-color: white;
  opacity: 0.85;
  backdrop-filter: blur(20px);
  z-index: 1;
  box-shadow: 0px 0px 10px rgba(0,0,0, .2);

  > div:first-of-type {
    margin-bottom: calc(var(--container-padding) * 0.5);
  }

  > ul + h1 {
    margin-top: calc(var(--container-padding) * 0.5);
  }
`;

const List = styled.ul`
  ${UnorderedList};
  list-style: none;
`;

SearchResult.propTypes = {
  schoolSearchResults: object,
  typeSearchResults: object,
};
