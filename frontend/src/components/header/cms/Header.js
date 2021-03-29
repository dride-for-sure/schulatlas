import styled from 'styled-components/macro';
import Image from '../../../resources/images/HeaderBackgroundSmall.jpg';
import Logo from '../../logo/Logo';
import Navigation from '../../navigation/cms/Navigation';
import FlexRowCenter from '../../structures/FlexRowCenter';
import FlexRowSpaceBetween from '../../structures/FlexRowSpaceBetween';
import MaxWidth from '../../structures/MaxWidth';

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
  ${FlexRowSpaceBetween};
  ${MaxWidth};
  padding: var(--container-padding) calc(var(--container-padding) * 2);
  
  > * {
    align-self:center;
  }
`;
