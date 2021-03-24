import { object } from 'prop-types';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components/macro';

export default function SideBarListItem({ type, page }) {
  return (
    <ListItem>
      {page && (<NavLink to={`/cms/page/${page.name}`}>{page.name}</NavLink>)}
      {type && (<NavLink to={`/cms/type/${type.name}`}>{`${type.name} (${type.count})`}</NavLink>)}
    </ListItem>
  );
}

const ListItem = styled.li``;

SideBarListItem.propTypes = {
  page: object,
  type: object,
};
