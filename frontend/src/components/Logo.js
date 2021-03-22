import logo from '../resources/images/Logo.png';
import ImageLink from './links/ImageLink';

export default function Logo() {
  return (
    <ImageLink to="/cms">
      <img style={{ height: 50 }} srcSet={logo} alt="Logo vom SCHULATLAS Projekt" />
    </ImageLink>
  );
}
