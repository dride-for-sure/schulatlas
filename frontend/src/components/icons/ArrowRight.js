import { bool } from 'prop-types';
import styled from 'styled-components';
import ArrowRightDark from '../../resources/images/ArrowRightDark.png';
import ArrowRightLight from '../../resources/images/ArrowRightLight.png';

export default function ArrowRight({ dark }) {
  return (
    <Arrow src={!dark ? ArrowRightLight : ArrowRightDark} alt="Fortfahren" />
  );
}

const Arrow = styled.img`
  margin: 0.15rem 0 0 0.6rem;
  height: 11px;
  width: 7px;
`;

ArrowRight.propTypes = {
  dark: bool,
};

ArrowRight.defaultProps = {
  dark: false,
};
