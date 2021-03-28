import { css } from 'styled-components/macro';

const EditGrid = css`
  display:grid;
  grid-template-columns: minmax(80px, 5%) 1fr;
  grid-template-rows: repeat();
  grid-template-areas:
    ". headline"
    "fields fields"
    ". submit";
    grid-gap: 0 var(--container-padding);

  > h1 {
    grid-area: headline;
  }

  > button {
    grid-area: submit;
    justify-self: right;
  }
`;

export default EditGrid;
