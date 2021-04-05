import { bool, func } from 'prop-types';
import styled from 'styled-components/macro';
import Search from '../icons/Search';
import EnvelopeButton from './_EnvelopeButton';

export default function SearchButton({ onClick, dark, disabled }) {
  return (
    <StyledButton disabled={disabled} type="button" dark={dark} onClick={onClick}>
      <Search dark={dark} />
    </StyledButton>
  );
}

const StyledButton = styled.button`
  ${EnvelopeButton}
  height: 32px;
  width: 30px;
  display:flex;
  justify-content: center;
  align-items: center;
`;

SearchButton.propTypes = {
  onClick: func,
  dark: bool.isRequired,
  disabled: bool.isRequired,
};
