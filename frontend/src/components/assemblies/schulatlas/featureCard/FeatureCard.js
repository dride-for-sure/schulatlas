import { object } from 'prop-types';
import styled, { css } from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';
import BackgroundCoverCenter from '../../../background/_BackgroundCoverCenter';
import HeadlineWithSubtitle from '../../../headlines/HeadlineWithSubtitle';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexColumnCenter from '../../../structures/_FlexColumnCenter';
import FlexColumnStart from '../../../structures/_FlexColumnStart';
import FlexRowSpaceBetween from '../../../structures/_FlexRowSpaceBetween';
import Paragraph from '../paragraph/Paragraph';

export default function FeatureCard({ assembly }) {
  const image = assembly.components.find((component) => component.type === 'image');
  const title = assembly.components.find((component) => component.type === 'title');
  const subtitle = assembly.components.find((component) => component.type === 'subtitle');
  const paragraph = assembly.components.find((component) => component.type === 'paragraph');

  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer variant={assembly.variant || ''}>
          <Image background={image.url} alt={image.description} />
          <ParagraphContainer>
            <HeadlineWithSubtitle size="l" title={title.content} subtitle={subtitle.content} margin />
            <Paragraph>{paragraph.content}</Paragraph>
          </ParagraphContainer>
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section``;

const PaddingContainer = styled.div`
  ${PaddingContainerL}
  padding-top: 0 !important;
  padding-bottom: 0 !important;

  @media ${minWidth.l} {
    padding: 0;
  }
`;

const MaxWidthContainer = styled.div`  
  ${FlexColumnStart};

  @media ${minWidth.l} {
    ${FlexRowSpaceBetween};

    ${(props) => props.variant === 'image-right' && css`
      flex-direction: row-reverse;
    `}
  }

  align-items: center;

  > div {
    width: 100%;
    box-sizing: border-box;
  }

  > div:first-of-type {
    height: calc(100vw / 3 * 1.5);
  }

  @media ${minWidth.l} {
    height: 40vw;
    max-height: 500px;
    
    > div,
    > div:first-of-type {
      width: 50%;
      height: 100%;
     } 
    }
`;

const Image = styled.div`
  ${BackgroundCoverCenter}
`;

const ParagraphContainer = styled.div`
  ${FlexColumnCenter}
  padding: 5vw 6vw 6vw;

  > p {
    display: -webkit-box;
    -webkit-line-clamp: 6;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;

FeatureCard.propTypes = {
  assembly: object.isRequired,
};
