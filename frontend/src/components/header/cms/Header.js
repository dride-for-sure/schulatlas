import styled from 'styled-components/macro';
import Image from '../../../resources/images/HeaderBackgroundSmall.jpg';
import BrandBar from '../../brandBar/BrandBar';
import Logo from '../../icons/Logo';
import Navigation from '../../navigation/cms/Navigation';
import FlexRowCenter from '../../structures/_FlexRowCenter';
import FlexRowSpaceBetween from '../../structures/_FlexRowSpaceBetween';
import MaxWidth from '../../structures/_MaxWidth';

export default function Header() {
  return (
    <>
      <Wrapper background={Image}>
        <Container>
          <Logo />
          <Navigation />
        </Container>
      </Wrapper>
      <BrandBar />
    </>
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
