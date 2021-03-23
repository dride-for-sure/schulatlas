import { string } from 'prop-types';
import styled, { css } from 'styled-components';
import ArrowRight from '../icons/ArrowRight';

export default function Button({ children, variant }) {
  return (
    <Container type="submit" variant={variant}>
      <span>{children}</span>
      <ArrowRight />
    </Container>
  );
}

const Container = styled.button`
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 170px;
  padding: 4px 5px 6px;
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

  > * {
    align-self: center;
  }

  > img {
    transition: var(--transition-transform);
  }

  :hover {
    opacity: var(--opacity-hover);

    > img {
      transform: translateX(3px);
    }
  }
`;

Button.propTypes = {
  children: string.isRequired,
  variant: string,
};

Button.defaultProps = {
  variant: '',
};
