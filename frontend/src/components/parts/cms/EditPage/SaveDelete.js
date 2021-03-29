import { func } from 'prop-types';
import styled from 'styled-components';
import MainButton from '../../../buttons/MainButton';

export default function SaveDelete({ onDelete }) {
  return (
    <Container>
      <MainButton type="button" variant="monochrome" onClick={() => onDelete()}>Delete</MainButton>
      <MainButton>Save</MainButton>
    </Container>
  );
}

const Container = styled.div`
  grid-area: submit;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;

  button + button {
    margin-left: var(--container-padding);
  }
`;

SaveDelete.propTypes = {
  onDelete: func.isRequired,
};
