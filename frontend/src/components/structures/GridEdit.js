import styled from 'styled-components/macro';
import TextEllipsis from '../text/_TextEllipsis';

const GridEdit = styled.div`
  display: grid;
  grid-template-columns: minmax(auto, 6rem) 1fr;
  grid-template-rows: repeat();
  grid-gap: var(--default-padding-s) var(--default-padding-s);
  height: fit-content;
  min-width: 0;

  > * {
    grid-column: 2 / span 1;
    min-width: 0;
    ${TextEllipsis}
  }

  > h1 {
    grid-column: 2 / span 1;
    margin: var(--default-margin) 0 0;

    &:first-of-type {
      margin: 0;
    }
  }

  > label {
    grid-column: 1 / span 1;
  }

  > button {
    margin: 0;
    justify-self: end;
  }
`;

export default GridEdit;
