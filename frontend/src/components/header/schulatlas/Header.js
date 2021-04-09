import { object } from 'prop-types';
import styled, { css } from 'styled-components/macro';
import minWidth from '../../../config/deviceBreakpoint';
import HeaderBackground from '../../../resources/images/HeaderBackgroundBig.jpg';
import HeroPrimary from '../../assemblies/schulatlas/hero/HeroPrimary';
import HeroSecondary from '../../assemblies/schulatlas/hero/HeroSecondary';
import BackgroundCoverCenter from '../../background/_BackgroundCoverCenter';
import BrandBar from '../../brandBar/_BrandBar';
import Logo from '../../icons/Logo';
import Navigation from '../../navigation/schulatlas/Navigation';
import PaddingContainerS from '../../padding/_PaddingContainerS';
import FlexColumnStart from '../../structures/_FlexColumnStart';
import FlexRowCenter from '../../structures/_FlexRowCenter';
import FlexRowSpaceBetween from '../../structures/_FlexRowSpaceBetween';
import MaxWidthL from '../../structures/_MaxWidthL';

export default function Header({ hero }) {
  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Logo />
          <Navigation />
        </MaxWidthContainer>
        <Background background={HeaderBackground} variant={hero.variant} />
      </PaddingContainer>
      {hero.variant === 'primary' ? <HeroPrimary assembly={hero} /> : <HeroSecondary assembly={hero} />}
    </Wrapper>
  );
}

const Wrapper = styled.header``;

const PaddingContainer = styled.div`
  ${FlexRowCenter};
  ${PaddingContainerS}
`;

const MaxWidthContainer = styled.nav`
  ${FlexRowSpaceBetween}
  ${MaxWidthL}
  position: relative;
  z-index: 2;
`;

const Background = styled.div`
  ${FlexColumnStart}
  ${BackgroundCoverCenter}

  align-items: center;
  position:absolute;
  top:0;
  left:0;
  height: 90vw;
  max-height: 600px;
  width: 100%;
  z-index: 1;

  @media ${minWidth.m} {
    height: 60vw;
    max-height: 615px;
    
    ${(props) => props.variant === 'secondary' && css`
      height: 100vw;
      max-height: 500px;
    `}
  }

  :after {
   ${BrandBar}
   bottom: 0;
  }
`;

Header.propTypes = {
  hero: object.isRequired,
};
