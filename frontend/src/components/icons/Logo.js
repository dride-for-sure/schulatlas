import { useLocation } from 'react-router-dom';
import styled from 'styled-components/macro';
import logo from '../../resources/images/Logo.png';
import ImageLink from '../links/ImageLink';

export default function Logo() {
  const { pathname } = useLocation();
  const isCMS = () => pathname.startsWith('/cms/');

  return (
    <ImageLink to={isCMS() ? '/cms/pages' : '/'}>
      <Image srcSet={logo} alt="Logo vom SCHULATLAS Projekt" />
    </ImageLink>
  );
}

const Image = styled.img`
  height: 45px;
  width: 45px;
`;
