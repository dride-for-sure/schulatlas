import { NavLink } from 'react-router-dom';
import styled from 'styled-components/macro';

const NavigationLink = styled(NavLink)`
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

export default NavigationLink;
