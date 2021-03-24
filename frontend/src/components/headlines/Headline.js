import styled, { css } from 'styled-components/macro';

const H1 = styled.h1`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-m);

  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-title-l);
  `}

  margin: var(--margin-title);
`;

export default H1;
