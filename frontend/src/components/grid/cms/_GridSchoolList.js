import { css } from 'styled-components/macro';

const GridSchoolList = css`
  display:grid;
  grid-template-columns: 0.5fr 2fr 1fr 0.6fr 0.2fr;
  grid-gap: var(--container-padding);
  min-height:0;
  min-width:0;

  > span {
    min-width: 0;
    white-space: nowrap;
    overflow: hidden;
  }

  // City & Date
  > span:nth-of-type(3), 
  > span:nth-of-type(4), 
  > span:last-of-type {
    text-align: right;
  }
`;

export default GridSchoolList;
