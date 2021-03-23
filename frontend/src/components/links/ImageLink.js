import { node, string } from 'prop-types';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

export default function ImageLink({ to, children }) {
  return (
    <Container>
      <Link to={to}>{children}</Link>
    </Container>
  );
}

const Container = styled.span`
  > a {
    outline: none;
    transition: var(--transition-opacity);

    :hover {
      opacity: var(--opacity-hover);
    }
  }
`;

ImageLink.propTypes = {
  to: string.isRequired,
  children: node.isRequired,
};
