import styled from 'styled-components/macro';

const GridOneTwo = styled.div`
  display: grid;
  grid-template-columns: 2fr min-content;
  grid-gap: 0 var(--container-padding);
`;

export default GridOneTwo;
