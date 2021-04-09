import { css } from 'styled-components/macro';

const GridSideBar = css`
  display: grid;
  grid-template-columns: 180px 1.5fr;
  grid-template-rows: 1fr;
  gap: 0 var(--default-padding-m);
  grid-template-areas: "SideBar Main";

  > div:first-of-type {
    grid-area: SideBar; 
  }

  > div:nth-of-type(2) {
    grid-area: Main;
  }
`;

export default GridSideBar;
