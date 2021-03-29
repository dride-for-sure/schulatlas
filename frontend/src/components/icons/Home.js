import styled from 'styled-components/macro';
import Home from '../../resources/images/Home.png';

export default function HomeIcon() {
  return (
    <Image src={Home} alt="Set landing page" />
  );
}

const Image = styled.img`
  height: 10px;
`;
