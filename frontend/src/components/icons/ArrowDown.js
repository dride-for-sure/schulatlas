import styled from 'styled-components/macro';
import ArrowDownDark from '../../resources/images/ArrowDownDark.png';

export default function ArrowDown() {
  return (
    <Arrow src={ArrowDownDark} alt="Next" />
  );
}

const Arrow = styled.img`
  height: 7px;
  width: 11px;
`;
