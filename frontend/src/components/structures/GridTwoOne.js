import styled from 'styled-components/macro';
import FlexColumnStart from './_FlexColumnStart';

const GridTwoOne = styled.div`
  display: grid;
  grid-template-columns: 2fr 180px;
  grid-gap: var(--default-padding-s);

  > div {
    ${FlexColumnStart}
  }
`;

export default GridTwoOne;
