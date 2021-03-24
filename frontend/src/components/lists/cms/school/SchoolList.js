import { array } from 'prop-types';
import styled from 'styled-components/macro';
import SchoolListItem from './SchoolListItem';

export default function SchoolList({ schools }) {
  return (
    <List>
      {schools.map((school) => <SchoolListItem key={school.number} school={school} />)}
    </List>
  );
}

const List = styled.ol``;

SchoolList.propTypes = {
  schools: array.isRequired,
};
