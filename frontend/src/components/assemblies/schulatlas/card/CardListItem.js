import { object } from 'prop-types';
import { useEffect, useState } from 'react';
import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';
import { getPageBySlug } from '../../../../services/api/public/pageApiService';
import Headline from '../../../headlines/Headline';
import Image from '../../../image/Image';
import ArrowLink from '../../../links/ArrowLink';
import Loading from '../../../loading/Loading';
import FlexColumnSpaceBetween from '../../../structures/_FlexColumnSpaceBetween';
import FlexColumnStart from '../../../structures/_FlexColumnStart';
import Paragraph from '../paragraph/Paragraph';

export default function CardListItem({ card }) {
  const [target, setTarget] = useState(null);

  const extractContent = (response) => {
    const hero = response.assemblies
      .find((assembly) => assembly.type === 'hero');
    return {
      title: hero.components.find((component) => component.type === 'title').content,
      subtitle: hero.components.find((component) => component.type === 'subtitle').content,
      imageUrl: hero.components.find((component) => component.type === 'image').url,
      imageDescription: hero.components.find((component) => component.type === 'image').description,
    };
  };

  useEffect(() => {
    if (card) {
      getPageBySlug(card.target)
        .then((response) => setTarget(extractContent(response)))
        .catch((error) => console.log(error));
    }
  }, []);

  if (!target) {
    return <Loading />;
  }

  return (
    <Wrapper>
      <Image src={target.imageUrl} alt={target.imageDescription} />
      <Content>
        <Headline>{target.title}</Headline>
        <Paragraph>{target.subtitle}</Paragraph>
        <ArrowLink to={`/${card.target}`} alt={target.title} />
      </Content>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  ${FlexColumnStart}
  flex: 0 0 100%;
  z-index: 2;
  background-color: white;
  box-shadow: var(--box-shadow);

  @media ${minWidth.m} {
    flex: 0 0 calc(100% / 2 - var(--default-padding-m2) / 2);
  }

  @media ${minWidth.l} {
    flex: 0 0 calc(100% / 3 - var(--default-padding-m2) * 2 / 3);
  }
`;

const Content = styled.div`
  ${FlexColumnSpaceBetween}
  height: 100%;
  padding: calc(var(--default-padding-s) * 0.8) var(--default-padding-s) calc(var(--default-padding-s) * 0.4); 

  > a {
    align-self: flex-end;
    margin: 0;
    padding: 0.5rem 0 0.5rem 0.5rem;
    transition: var(--transition-transform);

    :hover {
        transform: translateX(3px);
    }
  }
`;

CardListItem.propTypes = {
  card: object.isRequired,
};
