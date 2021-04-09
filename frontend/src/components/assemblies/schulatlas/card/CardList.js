import { object } from 'prop-types';
import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';
import BrandBar from '../../../brandBar/_BrandBar';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import MaxWidthL from '../../../structures/_MaxWidthL';
import MaxWidthM from '../../../structures/_MaxWidthM';
import MaxWidthS from '../../../structures/_MaxWidthS';
import CardListItem from './CardListItem';

export default function CardList({ assembly }) {
  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          {assembly && assembly.components.map((card) =>
            card.target && <CardListItem key={card.target} card={card} />)}
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.section`
  position: relative;

  :before {
    ${BrandBar}
    z-index: 2;
    top: calc(var(--default-padding-m2) + (100vw - var(--default-padding-s) * 2) / 3 * 2 - 4px);

    @media ${minWidth.xs} {
      top: calc(var(--default-padding-m2) + (var(--max-width-s) ) / 3 * 2 - 4px);
    }

    @media ${minWidth.s} {
      top: calc(var(--default-padding-l2) + (var(--max-width-s) ) / 3 * 2 - 4px);
    }

    @media ${minWidth.m} {
      top: calc(var(--default-padding-l2) + (var(--max-width-m) - var(--default-padding-m2) ) / 2 / 3 * 2 - 4px);
    }

    @media ${minWidth.l} {
      top: calc(var(--default-padding-xl) + (var(--max-width-l) - var(--default-padding-m2) * 2 ) / 3 / 3 * 2 - 4px);
    }
  }

  :after {
    content: '';
    position: absolute;
    background-color: var(--color-medium-silver);
    bottom: 0;
    left: 0;
    width: 100%;
    height: calc(100% - (var(--default-padding-m2) + (100vw - var(--default-padding-s) * 2) / 3 * 2) + 1px);
    z-index: 1;

    @media ${minWidth.xs} {
      height: calc(100% - (var(--default-padding-m2) + var(--max-width-s) / 3 * 2) + 1px);
    }

    @media ${minWidth.s} {
      height: calc(100% - (var(--default-padding-l2) + var(--max-width-s) / 3 * 2) + 1px);
    }

    @media ${minWidth.m} {
      height: calc(100% - (var(--default-padding-l2) + (var(--max-width-m) - var(--default-padding-m2) ) / 2 / 3 * 2) + 1px);
    }

    @media ${minWidth.l} {
      height: calc(100% - (var(--default-padding-xl) + (var(--max-width-l) - var(--default-padding-m2) * 2 ) / 3 / 3 * 2) + 1px);
    }
  }
`;

const PaddingContainer = styled.div`
  ${FlexRowCenter};
  ${PaddingContainerL}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthS}
  ${FlexRowCenter}
  flex-wrap: wrap;

  > div + div {
    margin-top: var(--default-padding-m2);
  }

  @media ${minWidth.m} {
    ${MaxWidthM}
    
    > div + div {
      margin: 0;
    }

    > div:first-of-type {
      margin-right: var(--default-padding-m2);
    }

    > div:last-of-type {
      margin-top: var(--default-padding-m2);
    }
  }

  @media ${minWidth.l} {
    ${MaxWidthL}

    > div:first-of-type {
      margin: 0;
    }

    > div:last-of-type {
      margin-top: 0;
    }

    > div + div {
      margin-left: var(--default-padding-m2);
    }
  }
`;

CardList.propTypes = {
  assembly: object.isRequired,
};
