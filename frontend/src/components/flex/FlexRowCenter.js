import { css } from 'styled-components/macro';

const FlexRowCenter = css`
  display:flex;
  flex-direction: row;
  justify-content: center;
  
  ${(props) => props.color === 'medium' && css`
    background-color: var(--color-medium-silver);
  `};

  ${(props) => props.color === 'dark' && css`
    background-color: var(--color-dark-silver);
  `};
`;

export default FlexRowCenter;
