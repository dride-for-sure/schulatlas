import styled from 'styled-components/macro';
import Image from '../../../resources/images/HeaderBackgroundSmall.jpg';
import FlexRowCenter from '../../flex/FlexRowCenter';
import Logo from '../../logo/Logo';
import Navigation from '../../navigation/cms/Navigation';

export default function Header() {
  return (
    <Wrapper background={Image}>
      <Container>
        <Logo />
        <Navigation />
      </Container>
    </Wrapper>
  );
}

const Wrapper = styled.header`
  background-image: url(${(props) => props.background});
  background-repeat: no-repeat;
  background-position: center;
  background-position: 50% 50%;
  background-size: cover;
  ${FlexRowCenter};
`;

const Container = styled.div`
  display:flex;
  justify-content: space-between;
  padding: var(--container-padding);
  width: 100%;
  
  > * {
    align-self:center;
  }
`;
