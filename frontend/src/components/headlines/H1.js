import { node, string } from 'prop-types';
import styled, { css } from 'styled-components';

export default function H1({ children, size }) {
  return (
    <Headline size={size}>
      {children}
    </Headline>
  );
}

const Headline = styled.h1`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-m);

  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-title-l);
  `}

  margin: var(--margin-title);
`;

H1.propTypes = {
  children: node.isRequired,
  size: string,
};
