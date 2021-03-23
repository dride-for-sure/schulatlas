import { object } from 'prop-types';
import styled from 'styled-components/macro';
import H1 from '../../headlines/H1';

export default function EditPage({ page }) {
  return (
    <Container>
      <H1>{page.name}</H1>
    </Container>
  );
}

const Container = styled.div``;

EditPage.propTypes = {
  page: object,
};

EditPage.defaultProps = {
  page: undefined,
};
