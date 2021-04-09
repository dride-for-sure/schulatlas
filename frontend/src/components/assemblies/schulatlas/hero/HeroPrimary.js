import { object } from 'prop-types';
import styled from 'styled-components/macro';
import minWidth, { maxWidth } from '../../../../config/deviceBreakpoint';
import HeadlineTwoRow from '../../../headlines/HeadlineTwoRow';
import Image from '../../../image/Image';
import BrandLink from '../../../links/BrandLink';
import PaddingContainerS from '../../../padding/_PaddingContainerS';
import FlexColumnEnd from '../../../structures/_FlexColumnEnd';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import MaxWidthL from '../../../structures/_MaxWidthL';

export default function HeroPrimary({ assembly }) {
  const title = assembly.components.find((component) => component.type === 'title');
  const subtitle = assembly.components.find((component) => component.type === 'subtitle');
  const button = assembly.components.filter((component) => component.type === 'button');
  const image = assembly.components.find((component) => component.type === 'image');

  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <PositionAbsolute>
            <CTA>
              <HeadlineTwoRow row1={title.content} row2={subtitle.content} size="l" variant="marked" />
              <ButtonGroup>
                <BrandLink url={button[0].target}>{button[0].content}</BrandLink>
                <BrandLink url={button[1].target} variant="monochrome">{button[1].content}</BrandLink>
              </ButtonGroup>
            </CTA>
          </PositionAbsolute>
          <Image src={image.url} alt={image.description} />
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section``;

const PaddingContainer = styled.div`
  ${FlexRowCenter};
  ${PaddingContainerS}
  padding-top: 0 !important;
  padding-bottom: 0 !important;
  position: relative;
  z-index: 2;
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL}
  position: relative;

  > img {
    max-height: 650px;
    object-fit: cover;
    box-shadow: var(--box-shadow);
  }
`;

const PositionAbsolute = styled.div`
  ${FlexColumnEnd}
  align-items: center;
  
  @media ${minWidth.m} {
    position: absolute;
    width: 100%;
    height: 100%;
  }
  
`;

const CTA = styled.div`
margin-bottom: var(--default-padding-m2);

@media ${maxWidth.m} {
  width: 100%;
  margin: var(--default-padding-s2) 0; 

  h2, h3 {
    display: inline-block;
    max-width: 100%;
    box-sizing: border-box;
    font-size: 5vw;
  }
}  
`;

const ButtonGroup = styled.div`
  display: none;

  @media ${minWidth.m} {
    ${FlexRowCenter}

    > a + a {
      margin-left: var(--default-padding-m2);
    }
    
    > a {
      margin-bottom: 0;
    }
  }
`;

HeroPrimary.propTypes = {
  assembly: object.isRequired,
};
