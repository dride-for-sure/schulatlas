import styled from 'styled-components/macro';

const GridTwoOne = styled.div`
  display: grid;
  grid-template-columns: 2fr 180px;
  grid-gap: var(--container-padding);

  > div {
    display: flex;
    flex-direction: column;
  }
`;

export default GridTwoOne;
