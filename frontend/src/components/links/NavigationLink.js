import { string } from 'prop-types';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

export default function NavigationLink({ to, children }) {
  return (
    <Container>
      <NavLink to={to}>{children}</NavLink>
    </Container>
  );
}

const Container = styled.span`
  > a {
    outline: none;
    text-decoration: none;
    color: white;
    transition: var(--transition-color);

    :hover {
      color: var(--color-gold-crayola);
    }
  }
`;

NavigationLink.propTypes = {
  to: string.isRequired,
  children: string.isRequired,
};
