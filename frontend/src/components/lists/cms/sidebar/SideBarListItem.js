import { object } from 'prop-types';
import styled from 'styled-components/macro';
import prettifySlug from '../../../../common/prettifySlug';
import RegularLink from '../../../links/RegularLink';

export default function SideBarListItem({ type, page }) {
  return (
    <ListItem>
      {page && (<RegularLink to={`/cms/page/${page.slug}`}>{prettifySlug(page.slug)}</RegularLink>)}
      {type && (<RegularLink to={`/cms/type/${type.name}`}>{`${type.name} (${type.count})`}</RegularLink>)}
    </ListItem>
  );
}

const ListItem = styled.li`
  font-size: var(--font-size-medium-content);
`;

SideBarListItem.propTypes = {
  page: object,
  type: object,
};
