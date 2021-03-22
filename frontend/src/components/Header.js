import styled from 'styled-components/macro';
import Background from '../resources/images/HeaderBackgroundSmall.jpg';
import Logo from './Logo';
import Navigation from './Navigation';
import Center from './structure/Center';
import MaxWidth from './structure/MaxWidth';

export default function Header() {
  return (
    <Container background={Background}>
      <Center>
        <MaxWidth>
          <Logo />
          <Navigation />
        </MaxWidth>
      </Center>
    </Container>
  );
}

const Container = styled.div`
  background-image: url(${(props) => props.background});
  background-repeat: no-repeat;
  background-position: center;
  background-position:50% 50%;
  background-size:cover;
  padding: var(--container-padding);
`;
