import { object } from 'prop-types';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

export default function SideBarListItem({ type, page }) {
  return (
    <Container>
      {page && (<NavLink to={`/cms/page/${page.name}`}>{page.name}</NavLink>)}
      {type && (<NavLink to={`/cms/schools/${type.name}`}>{`${type.name} (${type.count})`}</NavLink>)}
    </Container>
  );
}

const Container = styled.li``;

SideBarListItem.propTypes = {
  page: object,
  type: object,
};
