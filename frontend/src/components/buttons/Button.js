import { css } from 'styled-components/macro';
import FlexRowCenter from '../structures/FlexRowCenter';

const Button = css`
  ${FlexRowCenter}
  box-sizing: border-box;
  width: 180px;
  height: 32px;
  padding: 4px 5px 6px;
  margin: var(--default-margin) 0;
  border: 0;
  border-radius: var(--border-radius);
  outline: none;
  cursor: pointer;
  color: white;
  font-family: var(--font-family-button);
  font-size: var(--font-size-button);
  transition: var(--transition-opacity);
  background: var(--gradient-primary);
  
  ${(props) => props.variant === 'secondary' && css`
    background: var(--gradient-secondary);
  `}

  ${(props) => props.variant === 'monochrome' && css`
    background: var( --color-medium-silver);
  `}

  > * {
    align-self: center;
  }

  > img {
    transition: var(--transition-transform);
  }

  :not(:disabled):hover {
    opacity: var(--opacity-hover);

    > img {
      transform: translateX(3px);
    }
  }

  :disabled {
    opacity: var(--opacity-disabled);
    cursor: initial;
  }
`;

export default Button;
