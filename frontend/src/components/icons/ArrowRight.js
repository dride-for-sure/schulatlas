import { bool } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRightDark from '../../resources/images/ArrowRightDark.png';
import ArrowRightLight from '../../resources/images/ArrowRightLight.png';

export default function ArrowRight({ dark }) {
  return (
    <Arrow src={!dark ? ArrowRightLight : ArrowRightDark} alt="Next" />
  );
}

const Arrow = styled.img`
  height: 11px;
  width: 7px;
`;

ArrowRight.propTypes = {
  dark: bool,
};
