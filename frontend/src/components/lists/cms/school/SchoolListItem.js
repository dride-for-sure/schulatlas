import { object } from 'prop-types';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

export default function SchoolListItem({ school }) {
  return (
    <Container>
      <Link to={`/cms/school/${school.number}`}>{school.name}</Link>
    </Container>
  );
}

const Container = styled.li``;

SchoolListItem.propTypes = {
  school: object.isRequired,
};
