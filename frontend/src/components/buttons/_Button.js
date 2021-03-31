import { css } from 'styled-components/macro';
import FlexRowCenter from '../structures/_FlexRowCenter';
import EnvelopeButton from './_EnvelopeButton';

const Button = css`
  ${FlexRowCenter}
  ${EnvelopeButton}
  font-family: var(--font-family-button);
  font-size: var(--font-size-button);
  color: white;
  box-sizing: border-box;
  width: 180px;
  height: 32px;
  padding: 4px 5px 6px;
  margin: var(--default-margin) 0;
  border-radius: var(--border-radius);
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
