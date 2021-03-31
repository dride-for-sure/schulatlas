import { css } from 'styled-components/macro';

const GridEditDetails = css`
  display:grid;
  grid-template-columns: minmax(80px, 5%) 1fr;
  grid-template-rows: repeat();
  grid-template-areas:
    ". headline"
    "fields fields"
    ". submit";
  grid-gap: 0 var(--container-padding);
  height: fit-content;
  min-width: 0;

  > * {
    min-width: 0;
  }

  > h1 {
    grid-area: headline;
  }

`;

export default GridEditDetails;
