import { array } from 'prop-types';
import styled from 'styled-components/macro';
import H1 from '../../../headlines/H1';
import SideBarListItem from './SideBarListItem';

export default function SideBar({ pages, types }) {
  return (
    <Container>
      <H1>{pages ? 'Pages' : 'Schools'}</H1>
      <List>
        {pages && pages.map((page) =>
          <SideBarListItem key={page.name} page={page} />)}
        {types && types.map((type) =>
          <SideBarListItem key={type.name} type={type} />)}
      </List>
    </Container>
  );
}

const Container = styled.div``;

const List = styled.ol``;

SideBar.propTypes = {
  pages: array,
  types: array,
};
