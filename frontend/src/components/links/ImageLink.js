import { Link } from 'react-router-dom';
import styled from 'styled-components/macro';

const ImageLink = styled(Link)`
  outline: none;
  transition: var(--transition-opacity);

  :hover {
    opacity: var(--opacity-hover);
  }
`;

export default ImageLink;
