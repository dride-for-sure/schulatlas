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
          <AlignSelf>
            <Logo />
            <Navigation />
          </AlignSelf>
        </MaxWidth>
      </Center>
    </Container>
  );
}

const Container = styled.header`
  background-image: url(${(props) => props.background});
  background-repeat: no-repeat;
  background-position: center;
  background-position:50% 50%;
  background-size:cover;
  padding: var(--container-padding) 0;
`;

const AlignSelf = styled.div`
  display:flex;
  justify-content: space-between;
  
  > * {
    align-self:center;
  }
`;
