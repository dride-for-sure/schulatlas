import { css } from 'styled-components/macro';
import FlexRowCenter from '../structures/_FlexRowCenter';
import EnvelopeButton from './_EnvelopeButton';

const Button = css`
  ${FlexRowCenter}
  ${EnvelopeButton}
  position: relative;
  font-family: var(--font-family-button);
  font-size: var(--font-size-button);
  color: white;
  box-sizing: border-box;
  width: 180px;
  height: 32px;
  padding: 6px 5px 6px;
  margin: var(--default-margin) 0;
  border-radius: var(--border-radius);
  background: var(--gradient-primary);
  
  ${(props) => props.variant === 'secondary' && css`
    background: var(--gradient-secondary);
  `}

  ${(props) => props.variant === 'monochrome' && css`
    background: var( --color-medium-silver);
  `}

  :after {
    content: '';
    position: absolute;
    z-index: 1;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border-radius: var(--border-radius);
    opacity: 0;
    transition: var(--transition-opacity);
    background: var(--color-paradise-pink);
    
    ${(props) => props.variant === 'secondary' && css`
      background: var(--color-light-green);
    `}

    ${(props) => props.variant === 'monochrome' && css`
      background: var(--color-light-silver);
    `}
  }

  > * {
    align-self: center;
    z-index: 2;
  }

  > img {
    transition: var(--transition-transform);
  }

  :not(:disabled):hover {
    opacity: 1;
    
    > img {
      transform: translateX(3px);
    }

    :after {
      opacity: 1;
    }
  }

  :disabled {
    opacity: var(--opacity-disabled);
    cursor: initial;
  }
`;

export default Button;
