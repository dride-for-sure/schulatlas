import styled, { css } from 'styled-components/macro';

const Headline = styled.h1`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-m);

  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-title-l);
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
  
  text-transform: capitalize;

  > i {
    font-weight: 400;
  }
`;

export default Headline;
