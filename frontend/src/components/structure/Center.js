import { node, string } from 'prop-types';
import styled, { css } from 'styled-components/macro';

export default function Center({ children, variant }) {
  return (
    <Container variant={variant}>
      {children}
    </Container>
  );
}

const Container = styled.div`
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

Center.propTypes = {
  children: node.isRequired,
  variant: string.isRequired,
};
