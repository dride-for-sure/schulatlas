import { object } from 'prop-types';
import { Link } from 'react-router-dom';
import styled from 'styled-components/macro';

export default function SchoolListItem({ school }) {
  return (
    <ListItem>
      <Link to={`/cms/school/${school.number}`}>{school.name}</Link>
    </ListItem>
  );
}

const ListItem = styled.li``;

SchoolListItem.propTypes = {
  school: object.isRequired,
};
