import { array } from 'prop-types';
import styled from 'styled-components';
import SchoolListItem from './SchoolListItem';

export default function SchoolList({ schools }) {
  return (
    <Container>
      <List>
        {schools.map((school) => <SchoolListItem key={school.number} school={school} />)}
      </List>
    </Container>
  );
}

const Container = styled.div``;

const List = styled.ol``;

SchoolList.propTypes = {
  schools: array.isRequired,
};
