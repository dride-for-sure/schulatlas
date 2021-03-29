import { array } from 'prop-types';
import styled from 'styled-components';
import getSumOfTypes from '../../../../common/types';
import RegularLink from '../../../links/RegularLink';

export default function SideBarListHeader({ types }) {
  return (
    <ListItem>
      <RegularLink to="/cms/schools">{`All Types (${getSumOfTypes(types)})`}</RegularLink>
    </ListItem>
  );
}

const ListItem = styled.li`
  font-size: var(--font-size-medium-content);
`;

SideBarListHeader.propTypes = {
  types: array.isRequired,
};
