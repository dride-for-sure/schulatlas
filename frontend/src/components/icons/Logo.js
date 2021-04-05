import styled from 'styled-components/macro';
import logo from '../../resources/images/Logo.png';
import ImageLink from '../links/ImageLink';

export default function Logo() {
  return (
    <ImageLink to="/cms/pages">
      <Image srcSet={logo} alt="Logo vom SCHULATLAS Projekt" />
    </ImageLink>
  );
}

const Image = styled.img`
  height: 45px;
`;
