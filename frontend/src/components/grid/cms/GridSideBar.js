import styled from 'styled-components/macro';

const GridSideBar = styled.main`
  display: grid;
  grid-template-columns: 180px 1.5fr;
  grid-template-rows: 1fr;
  gap: 0 calc(var(--container-padding) * 2);
  grid-template-areas: "SideBar Main";
  padding: calc(var(--container-padding) * 3) calc(var(--container-padding) * 2);
  max-width: var(--container-max-width);
  width: 100%;

  > div:first-of-type {
    grid-area: SideBar; 
  }

  > div:nth-of-type(2) {
    grid-area: Main;
  }
`;

export default GridSideBar;
