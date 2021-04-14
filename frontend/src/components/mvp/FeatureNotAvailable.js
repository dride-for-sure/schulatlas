import styled from 'styled-components';
import HeaderBackground from '../../resources/images/HeaderBackgroundBig.jpg';
import Footer from '../assemblies/schulatlas/footer/Footer';
import BackgroundCoverCenter from '../background/_BackgroundCoverCenter';
import Header from '../header/error/Header';
import FlexColumnSpaceBetween from '../structures/_FlexColumnSpaceBetween';
import FeatureNotAvailableMessage from './FeatureNotAvailableMessage';

export default function FeatureNotAvailable() {
  return (
    <Wrapper background={HeaderBackground}>
      <Header />
      <FeatureNotAvailableMessage />
      <Footer />
    </Wrapper>
  );
}

const Wrapper = styled.section`
  ${BackgroundCoverCenter}
  ${FlexColumnSpaceBetween}
  height: 100vh;
  width: 100vw;
`;
