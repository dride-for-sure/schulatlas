import { array, func } from 'prop-types';
import styled from 'styled-components/macro';
import UnorderedList from '../../_UnorderedList';
import SideBarListHeader from './SideBarListHeader';
import SideBarListItem from './SideBarListItem';

export default function SideBarList({ pages, types, setLandingPage }) {
  if (pages) {
    return (
      <List>
        {pages.map((page) =>
          <SideBarListItem key={page.slug} page={page} setLandingPage={setLandingPage} />)}
      </List>
    );
  }

  return (
    <List>
      <SideBarListHeader types={types} />
      {types.map((type) =>
        <SideBarListItem key={type.slug} type={type} />)}
    </List>
  );
}

const List = styled.ul`
  ${UnorderedList};
`;

SideBarList.propTypes = {
  pages: array,
  types: array,
  setLandingPage: func,
};
