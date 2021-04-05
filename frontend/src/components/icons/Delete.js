import { bool } from 'prop-types';
import styled from 'styled-components/macro';
import DeleteDark from '../../resources/images/DeleteDark.png';
import DeleteWhite from '../../resources/images/DeleteWhite.png';

export default function Delete({ dark }) {
  return (
    <Image src={dark ? DeleteDark : DeleteWhite} alt="Delete" />
  );
}

const Image = styled.img`
  height: 11px;
`;

Delete.propTypes = {
  dark: bool.isRequired,
};
