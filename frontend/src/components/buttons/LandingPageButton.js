import { bool, func } from 'prop-types';
import styled from 'styled-components';
import Home from '../../resources/images/Home.png';

export default function LandingPageButton({ inactive, onClick }) {
  return (
    <CustomButton type="button" onClick={onClick} inactive={inactive}>
      <img src={Home} alt="Set Landing Page" />
    </CustomButton>
  );
}

const CustomButton = styled.button`
  outline: none;
  border: none;
  background: transparent;
  cursor: pointer;
  opacity: ${(props) => (props.inactive ? '0.3' : '1')};
  transition: var(--transition-opacity);

  :hover {
    opacity: 1;
  }

  > img {
    height :10px;
  }
`;

LandingPageButton.propTypes = {
  inactive: bool.isRequired,
  onClick: func.isRequired,
};
