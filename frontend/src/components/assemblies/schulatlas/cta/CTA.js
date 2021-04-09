import { object } from 'prop-types';
import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';
import Headline from '../../../headlines/Headline';
import BrandLink from '../../../links/BrandLink';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexColumnCenter from '../../../structures/_FlexColumnCenter';
import FlexColumnStart from '../../../structures/_FlexColumnStart';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import MaxWidthM from '../../../structures/_MaxWidthM';
import Paragraph from '../paragraph/Paragraph';

export default function CTA({ assembly }) {
  const title = assembly.components.find((component) => component.type === 'title').content;
  const paragraph = assembly.components.find((component) => component.type === 'paragraph').content;
  const buttons = assembly.components.filter((component) => component.type === 'button');

  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Headline size="l">{title}</Headline>
          <Paragraph>{paragraph}</Paragraph>
          <Buttons>
            <BrandLink to={buttons[0].target}>{buttons[0].content}</BrandLink>
            <BrandLink to={buttons[1].target} variant="monochrome">{buttons[1].content}</BrandLink>
          </Buttons>
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section`
  background-color: var(--color-dark-silver);
`;

const PaddingContainer = styled.div`
  ${FlexRowCenter}
  ${PaddingContainerL}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthM}
  ${FlexColumnStart};
  align-items: center;
  color: white;

  > h1 {
    width: 100%;
    text-align: center;
  }
`;

const Buttons = styled.div`
  ${FlexColumnCenter};
  
  > a {
    margin-bottom: 0;
  }

  > a + a {
    margin-top: var(--default-margin);
  }

  @media ${minWidth.s} {
    ${FlexRowCenter}

    > a + a {
    margin-left: var(--default-padding-m2);
  }
}
`;

CTA.propTypes = {
  assembly: object.isRequired,
};
