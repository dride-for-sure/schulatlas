import { bool, func } from 'prop-types';
import styled, { css } from 'styled-components/macro';
import Delete from '../icons/Delete';
import EnvelopeButton from './_EnvelopeButton';

export default function DeleteButton({ onClick, dark }) {
  return (
    <StyledButton type="button" dark={dark} onClick={onClick}>
      <Delete dark={dark} />
    </StyledButton>
  );
}

const StyledButton = styled.button`
  ${EnvelopeButton}
  background: ${(props) => !props.dark && css`var( --color-medium-silver)`};
  border-radius: var(--border-radius);
  height: 32px;
  width: 30px;
  display:flex;
  justify-content: center;
  align-items: center;
`;

DeleteButton.propTypes = {
  onClick: func.isRequired,
  dark: bool.isRequired,
};
