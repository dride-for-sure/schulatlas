import styled, { css } from 'styled-components/macro';
import CapitalizeFirstLetter from '../text/_CapitalizeFirstLetter';

const Headline = styled.h1`
  ${CapitalizeFirstLetter}
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-m);

  ${(props) => props.size === 'xl' && css`
    font-size: var(--font-size-title-xl);
  `}

  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-title-l);
  `}

  ${(props) => props.size === 's2' && css`
    font-size: var(--font-size-title-s2);
  `}

  ${(props) => props.size === 's' && css`
    font-size: var(--font-size-title-s);
  `}

  ${(props) => props.size === 'xs' && css`
    font-size: var(--font-size-title-xs);
  `}

  margin: var(--margin-title);

  ${(props) => props.size === 's' && css`
    margin: var(--margin-title-s);
  `}

  ${(props) => props.before && css`
    :before {
      content: '${`${props.before}:`}';
      margin-right: 0.4rem;
      font-style: italic;
      font-weight: 400;
    }
  `}
`;

export default Headline;
