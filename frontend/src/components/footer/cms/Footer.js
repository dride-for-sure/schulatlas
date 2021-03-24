import styled from 'styled-components/macro';
import FlexRowCenter from '../../flex/FlexRowCenter';

export default function Footer() {
  return (
    <Wrapper>
      <Container>
        <p>Footer</p>
      </Container>
    </Wrapper>
  );
}

const Wrapper = styled.footer`
  position: fixed;
  bottom: 0;
  background-color: var(--color-medium-silver);
  width: 100%;
  ${FlexRowCenter};
`;

const Container = styled.div`
  color: white;
  max-width: var(--max-width);
`;
