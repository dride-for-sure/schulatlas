import styled from 'styled-components/macro';
import Error from '../../resources/images/Error.png';
import Headline from '../headlines/Headline';
import Image from '../image/Image';
import PaddingContainerM from '../padding/_PaddingContainerM';
import FlexColumnCenter from '../structures/_FlexColumnCenter';
import FlexRowCenter from '../structures/_FlexRowCenter';
import MaxWidthL from '../structures/_MaxWidthL';

export default function ErrorMessage() {
  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Image src={Error} alt="Smiley vs Error" />
          <Headline size="xl">Ups!</Headline>
          <Headline size="l">Das ist ging nach hinten los...</Headline>
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section``;

const PaddingContainer = styled.div`
  ${PaddingContainerM}
  ${FlexRowCenter}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL}
  ${FlexColumnCenter}
  align-items: center;
  color: white;
  text-align: center;

  > img {
    height: 25vw;
    max-height: 200px;
    width: auto;
    padding-bottom: 1.5rem;
  }
`;
