import styled from 'styled-components';
import Center from './structure/Center';
import MaxWidth from './structure/MaxWidth';

export default function Footer() {
  return (
    <FixedBottom>
      <Center>
        <MaxWidth>
          <Container>
            <p>Footer</p>
          </Container>
        </MaxWidth>
      </Center>
    </FixedBottom>
  );
}

const FixedBottom = styled.footer`
  position: fixed;
  bottom: 0;
  background-color: var(--color-medium-silver);
  width: 100%;
`;

const Container = styled.div`
  color: white;
`;
