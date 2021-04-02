import styled from 'styled-components/macro';
import DeleteWhite from '../../resources/images/DeleteWhite.png';

export default function Delete() {
  return (
    <Image src={DeleteWhite} alt="Delete" />
  );
}

const Image = styled.img`
  height: 11px;
`;
