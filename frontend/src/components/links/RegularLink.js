import { Link } from 'react-router-dom';
import styled from 'styled-components/macro';

const RegularLink = styled(Link)`
  outline: none;
  text-decoration: none;
  color: inherit;
  transition: var(--transition-opacity);

  :hover {
    opacity: var(--opacity-hover);
  }
`;

export default RegularLink;
