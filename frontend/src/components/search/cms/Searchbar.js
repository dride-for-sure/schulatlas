import { func, object, string } from 'prop-types';
import styled from 'styled-components/macro';
import DeleteButton from '../../buttons/DeleteButton';
import SearchButton from '../../buttons/SearchButton';
import Input from '../../form/Input';

export default function SearchBar({ searchString, onSearch, onLeave, inputRef }) {
  return (
    <Container>
      <Input
        ref={inputRef}
        align="left"
        id="searchbar"
        placeholder="Search in schulatlas..."
        type="text"
        value={searchString || ''}
        onChange={onSearch} />
      {searchString && <DeleteButton onClick={onLeave} dark />}
      {!searchString && <SearchButton disabled dark />}
    </Container>
  );
}

const Container = styled.div`
  position:relative;

  > input {
    padding-right: 2.3rem;
  }

  > button {
    position: absolute;
    top: 0;
    right: 0.4rem;
  }

`;

SearchBar.propTypes = {
  inputRef: object.isRequired,
  searchString: string.isRequired,
  onSearch: func.isRequired,
  onLeave: func.isRequired,
};
