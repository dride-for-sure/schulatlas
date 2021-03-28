import { object } from 'prop-types';
import styled from 'styled-components/macro';

export default function EditSchool({ school }) {
  return (
    <Container>
      {school.name}
    </Container>
  );
}

const Container = styled.div``;

EditSchool.propTypes = {
  school: object.isRequired,
};
