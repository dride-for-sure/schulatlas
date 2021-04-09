import { object } from 'prop-types';
import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';
import HeadlineWithSubtitle from '../../../headlines/HeadlineWithSubtitle';
import Image from '../../../image/Image';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexColumnStart from '../../../structures/_FlexColumnStart';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import MaxWidthL from '../../../structures/_MaxWidthL';
import Paragraph2Columns from '../paragraph/Paragraph2Columns';

export default function TextBlock({ assembly }) {
  const title = assembly.components.find((component) => component.type === 'title').content;
  const subtitle = assembly.components.find((component) => component.type === 'subtitle').content;
  const paragraphs = assembly.components.filter((component) => component.type === 'paragraph');
  const image = assembly.components.find((component) => component.type === 'image');

  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Paragraph2Columns>
            <HeadlineWithSubtitle size="l" margin title={title} subtitle={subtitle} />
            {paragraphs[0].content}
          </Paragraph2Columns>
          <Image src={image.url} alt={image.description} />
          <Paragraph2Columns>{paragraphs[1].content}</Paragraph2Columns>
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section``;

const PaddingContainer = styled.div`
  ${FlexRowCenter};
  ${PaddingContainerL}
  padding-bottom: 0 !important;
`;

const MaxWidthContainer = styled.div`
  ${FlexColumnStart};
  ${MaxWidthL}
  align-items: center;

  > img {
    width: 100%;
    margin: var(--default-padding-s2);

    @media ${minWidth.m} {
      width: 70%;
      margin: var(--default-padding-m2);
    }
  }
`;

TextBlock.propTypes = {
  assembly: object.isRequired,
};
