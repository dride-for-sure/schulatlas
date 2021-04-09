import { object } from 'prop-types';
import styled from 'styled-components/macro';
import Image from '../../../image/Image';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexColumnCenter from '../../../structures/_FlexColumnCenter';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import MaxWidthL from '../../../structures/_MaxWidthL';

export default function Sponsors({ assembly }) {
  const title = assembly.components.find((component) => component.type === 'title');
  const images = assembly.components.filter((component) => component.type === 'image');

  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Headline>{title.content}</Headline>
          <SponsorImages>
            {images.length > 0 && images.map((image) =>
              <Image key={image.url} src={image.url} />)}
          </SponsorImages>
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section``;

const PaddingContainer = styled.div`
  ${FlexRowCenter}
  ${PaddingContainerL}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL}
  ${FlexColumnCenter};
  align-items: center;
`;

const Headline = styled.h3`
  font-family:  var(--font-family-subtitle);
  font-style: var(--font-style-subtitle);
  font-weight: var(--font-weight-title-extraslim);
  font-size: var(--font-size-content);
  margin-bottom: var(--default-padding-s2);
`;

const SponsorImages = styled.div`
  ${FlexRowCenter}
  
  > img {
    height: 35px;
    width: auto;

    + img {
      margin-left: var(--default-padding-m2);
    }
  }
`;

Sponsors.propTypes = {
  assembly: object.isRequired,
};
