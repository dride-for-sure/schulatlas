import styled from 'styled-components/macro';
import BrandBar from '../../../brandBar/_BrandBar';

export default function Atlas() {
  return (
    <Wrapper>
      <p>Atlas</p>
    </Wrapper>
  );
}

const Wrapper = styled.main`
  height: 100vh;
  width: 100%;

  :after {
    ${BrandBar}
    position: position;
    bottom: 0;
    z-index: 0;
  }
`;
