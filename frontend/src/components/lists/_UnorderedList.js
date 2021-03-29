import { css } from 'styled-components/macro';

const UnorderedList = css`
  list-style-position: inside;
  text-transform: capitalize;

  > li + li {
    margin-top: calc(var(--default-margin) * 0.15);
  }
`;

export default UnorderedList;
