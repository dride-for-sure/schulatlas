import styled from 'styled-components/macro';

const GridMaxOne = styled.div`
  display: grid;
  grid-template-columns: 1fr min-content;
  grid-gap: var(--container-padding);
`;

export default GridMaxOne;
