import { object } from 'prop-types';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

export default function SideBarListItem({ category, page }) {
  return (
    <Container>
      {page && (<NavLink to={`/cms/page/${page.name}`}>{page.name}</NavLink>)}
      {category && (<NavLink to={`/cms/schools/${category.name}`}>{category.name}</NavLink>)}
    </Container>
  );
}

const Container = styled.li``;

SideBarListItem.propTypes = {
  page: object,
  category: object,
};

SideBarListItem.defaultProps = {
  page: undefined,
  category: undefined,
};
