import { object } from 'prop-types';
import styled from 'styled-components/macro';
import { maxWidth } from '../../../../config/deviceBreakpoint';
import HeadlineTwoRowWithSubtitle from '../../../headlines/HeadlineTwoRowWithSubtitle';
import Image from '../../../image/Image';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import MaxWidthL from '../../../structures/_MaxWidthL';

export default function HeroSecondary({ assembly }) {
  const title1 = assembly.components.find((component) => component.type === 'title row 1');
  const title2 = assembly.components.find((component) => component.type === 'title row 2');
  const subtitle = assembly.components.find((component) => component.type === 'subtitle');
  const image = assembly.components.find((component) => component.type === 'image');

  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <HeadlineTwoRowWithSubtitle row1={title1.content} row2={title2.content} subtitle={subtitle.content} size="l" variant="marked" />
          <Image src={image.url} alt={image.description} />
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section``;

const PaddingContainer = styled.div`
  ${FlexRowCenter};
  ${PaddingContainerL}
  padding-top: var(--default-padding-s2); 
  padding-bottom: 0 !important;
  position: relative;
  z-index: 2;
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL}

  > img {
    max-height: 350px;
    object-fit: cover;
    box-shadow: var(--box-shadow);
  }

  @media ${maxWidth.m} {
    h2, h3 {
      display: inline-block;
      max-width: 100%;
      box-sizing: border-box;
      font-size: 5vw;
    }

    h4 {
      margin-top: 0.4rem;
    }
  }
`;

HeroSecondary.propTypes = {
  assembly: object.isRequired,
};
