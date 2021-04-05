import { array } from 'prop-types';
import styled from 'styled-components/macro';
import { getSumOfTypes } from '../../../../common/types';
import RegularLink from '../../../links/RegularLink';

export default function SideBarListHeader({ usedTypes }) {
  return (
    <ListItem>
      <RegularLink to="/cms/schools">{`All Types (${getSumOfTypes(usedTypes)})`}</RegularLink>
    </ListItem>
  );
}

const ListItem = styled.li`
  font-size: var(--font-size-medium-content);
`;

SideBarListHeader.propTypes = {
  usedTypes: array.isRequired,
};
