import styled from 'styled-components/macro';

const GridSchoolList = styled.div`
  display:grid;
  grid-template-columns: minmax(auto, 6rem) 2fr 0.8fr 0.6fr 0.4fr;
  grid-template-rows: repeat();
  grid-gap:  calc(var(--container-padding) * 0.5) var(--container-padding);
  height: fit-content;
  min-height: 0;
  min-width: 0;

  > * {
    min-width: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  > h1 {
    grid-column: 2 / span 4;
  }

  > button:first-of-type {
    grid-column: 1 / span 1;
  }

  > span {
    min-width: 0;
    white-space: nowrap;
    overflow: hidden;
  }

  > div:last-of-type {
    grid-column: 4 / span 2;
  }
`;

export default GridSchoolList;
