import { css } from 'styled-components/macro';

const UnorderedList = css`
  list-style-position: inside;
  text-transform: capitalize;
  
  > li {
    height: 1rem;
  }

  > li + li {
    margin-top: calc(var(--default-margin) * 0.2);
  }
`;

export default UnorderedList;
