import { bool } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRightDark from '../../resources/images/ArrowRightDark.png';
import ArrowRightLight from '../../resources/images/ArrowRightLight.png';

export default function ArrowLeft({ dark }) {
  return (
    <Arrow src={!dark ? ArrowRightLight : ArrowRightDark} alt="Next" />
  );
}

const Arrow = styled.img`
  height: 11px;
  width: 7px;
  -webkit-transform: scaleX(-1);
  transform: scaleX(-1);
`;

ArrowLeft.propTypes = {
  dark: bool,
};
