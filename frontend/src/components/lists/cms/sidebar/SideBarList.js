import { array, func } from 'prop-types';
import styled from 'styled-components/macro';
import TextEllipsis from '../../../text/_TextEllipsis';
import UnorderedList from '../../_UnorderedList';
import SideBarListHeader from './SideBarListHeader';
import SideBarListItem from './SideBarListItem';

export default function SideBarList({ pages, usedTypes, setLandingPage }) {
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
      <SideBarListHeader key="AllTypes" usedTypes={usedTypes} />
      {usedTypes.map((type) => type.name && <SideBarListItem key={type.name} type={type} />)}
    </List>
  );
}

const List = styled.ul`
  ${UnorderedList};
  
  > li {
    ${TextEllipsis}
  }
`;

SideBarList.propTypes = {
  pages: array,
  usedTypes: array,
  setLandingPage: func,
};
