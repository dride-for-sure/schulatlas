import styled from 'styled-components/macro';

const GridEdit = styled.div`
  display:grid;
  grid-template-columns: minmax(auto, 6rem) 1fr;
  grid-template-rows: repeat();
  grid-gap: var(--container-padding) var(--container-padding);
  height: fit-content;
  min-width: 0;

  > * {
    grid-column: 2 / span 1;
    min-width: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
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
