import styled from 'styled-components/macro';

const UnorderedList = styled.ul`
  list-style-position: inside;
  margin: var(--default-margin) 0;
  text-transform: capitalize;

  > li + li {
    margin-top: calc(var(--default-margin) * 0.15);
  }
`;

export default UnorderedList;
