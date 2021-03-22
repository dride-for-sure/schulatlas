import { node } from 'prop-types';
import styled from 'styled-components/macro';

export default function MaxWidth({ children }) {
  return (
    <Container>
      {children}
    </Container>
  );
}

const Container = styled.div`
  max-width: var(--container-max-width);
  width: 100%;
`;

MaxWidth.propTypes = {
  children: node.isRequired,
};
