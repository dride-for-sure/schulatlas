import { bool } from 'prop-types';
import styled from 'styled-components/macro';
import SearchDark from '../../resources/images/SearchDark.png';
import SearchWhite from '../../resources/images/SearchWhite.png';

export default function Search({ dark }) {
  return (
    <Image src={dark ? SearchDark : SearchWhite} alt="Search" />
  );
}

const Image = styled.img`
  height: 16px;
`;

Search.propTypes = {
  dark: bool.isRequired,
};
