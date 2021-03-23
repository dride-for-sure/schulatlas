import { array } from 'prop-types';
import styled from 'styled-components/macro';
import H1 from '../../headlines/H1';
import SideBarListItem from './SideBarListItem';

export default function SideBar({ pages, categories }) {
  return (
    <Container>
      <H1>{pages ? 'Pages' : 'Schools'}</H1>
      {pages && pages.map((page) =>
        <SideBarListItem key={page.name} page={page} />)}
      {categories && categories.map((category) =>
        <SideBarListItem key={category.name} category={category} />)}
    </Container>
  );
}

const Container = styled.div``;

SideBar.propTypes = {
  pages: array,
  categories: array,
};
