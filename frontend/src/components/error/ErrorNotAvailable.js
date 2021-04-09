import { object } from 'prop-types';
import styled from 'styled-components';
import HeaderBackground from '../../resources/images/HeaderBackgroundBig.jpg';
import Footer from '../assemblies/schulatlas/footer/Footer';
import BackgroundCoverCenter from '../background/_BackgroundCoverCenter';
import Header from '../header/error/Header';
import FlexColumnSpaceBetween from '../structures/_FlexColumnSpaceBetween';
import ErrorMessage from './ErrorMessage';

export default function ErrorNotAvailable({ error }) {
  console.log(error);

  return (
    <Wrapper background={HeaderBackground}>
      <Header />
      <ErrorMessage />
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

ErrorNotAvailable.propTypes = {
  error: object.isRequired,
};
