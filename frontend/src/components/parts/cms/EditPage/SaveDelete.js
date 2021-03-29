import { func } from 'prop-types';
import styled from 'styled-components';
import MainButton from '../../../buttons/BrandButton';
import FlexRowEnd from '../../../structures/_FlexRowEnd';

export default function SaveDelete({ onDelete }) {
  return (
    <Container>
      <MainButton type="button" variant="monochrome" onClick={() => onDelete()}>Delete</MainButton>
      <MainButton>Save</MainButton>
    </Container>
  );
}

const Container = styled.div`
  ${FlexRowEnd}
  grid-area: submit;

  button + button {
    margin-left: var(--container-padding);
  }
`;

SaveDelete.propTypes = {
  onDelete: func.isRequired,
};
