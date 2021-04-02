import { bool, func } from 'prop-types';
import styled from 'styled-components';
import Home from '../icons/Home';
import EnvelopeButton from './_EnvelopeButton';

export default function HomeButton({ inactive, onClick }) {
  return (
    <Button type="button" onClick={onClick} inactive={inactive}>
      <Home />
    </Button>
  );
}

const Button = styled.button`
  ${EnvelopeButton}
  margin-right: .4rem;

  :hover {
    opacity: 1;
  }
`;

HomeButton.propTypes = {
  inactive: bool.isRequired,
  onClick: func.isRequired,
};
