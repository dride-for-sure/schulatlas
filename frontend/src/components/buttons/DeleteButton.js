import { func } from 'prop-types';
import styled from 'styled-components';
import Delete from '../icons/Delete';
import EnvelopeButton from './_EnvelopeButton';

export default function DeleteButton({ onClick }) {
  return (
    <StyledButton type="button" onClick={onClick}>
      <Delete />
    </StyledButton>
  );
}

const StyledButton = styled.button`
  ${EnvelopeButton}
  background: var( --color-medium-silver);
  border-radius: var(--border-radius);
  height: 32px;
  margin: 0;
  width: 30px;
  display:flex;
  justify-content: center;
  align-items: center;
  transition: var(--transition-opacity);
  opacity: 1;

  :hover {
    opacity: var(--opacity-hover);
  }
`;

DeleteButton.propTypes = {
  onClick: func.isRequired,
};
