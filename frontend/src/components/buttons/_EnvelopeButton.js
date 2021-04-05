import { css } from 'styled-components/macro';

const EnvelopeButton = css`
  border: 0;
  outline: none;
  margin: 0;
  padding: 0;
  color: var(--color-medium-silver);
  font-family: var(--font-family-content);
  font-size: var(--font-size-content);
  transition: var(--transition-opacity);
  background: transparent;
  opacity: ${(props) => (props.inactive ? '0.3' : '1')};
  transition: var(--transition-opacity);

  :not(:disabled):hover {
    opacity: var(--opacity-hover);
    cursor: pointer;
  }
`;

export default EnvelopeButton;
