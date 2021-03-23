import { node } from 'prop-types';
import styled from 'styled-components';

export default function CmsGrid({ children }) {
  return (
    <Container>
      {children}
    </Container>
  );
}

const Container = styled.main`
  display: grid;
  grid-template-columns: 0.5fr 1.5fr;
  grid-template-rows: 1fr;
  gap: 0px 0px;
  grid-template-areas: "SideBar Main";
  padding: calc(var(--container-padding) * 3) 0;

  > div:first-of-type {
    grid-area: SideBar; 
  }

  > div:last-of-type {
    grid-area: ListMainPage;
  }
`;

CmsGrid.propTypes = {
  children: node.isRequired,
};
