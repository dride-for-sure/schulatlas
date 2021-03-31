import { func, object, string } from 'prop-types';
import styled from 'styled-components/macro';
import Headline from '../../../headlines/Headline';
import OrderedList from '../../_OrderedList';
import Pagination from './Pagination';
import SchoolListHeader from './SchoolListHeader';
import SchoolListItem from './SchoolListItem';

export default function SchoolList({ type, schools, onPagination, toggleSort }) {
  return (
    <Container>
      <Headline>{type || 'All Types'}</Headline>
      <List>
        <SchoolListHeader toggleSort={toggleSort} />
        {schools.content.map((school) => <SchoolListItem key={school.number} school={school} />)}
      </List>
      <Pagination pageable={schools} onPagination={onPagination} />
    </Container>
  );
}

const Container = styled.div`
  > h1 {
    padding: 0 calc(var(--container-padding) * 0.5);
  }
`;

const List = styled.ol`
  ${OrderedList};

  > li {
    padding: calc(var(--container-padding) * 0.2) calc(var(--container-padding) * 0.5);

    :first-of-type {
      margin-bottom: calc(var(--container-padding) * 0.5);
      border-bottom: 1px solid rgba(0,0,0,0.1);
      border-radius: 1px;
      user-select: none;
    }

    :nth-of-type(2n+3) {
      background-color: rgba(0,0,0,0.02);
    }
  }
`;

SchoolList.propTypes = {
  type: string.isRequired,
  schools: object.isRequired,
  toggleSort: func.isRequired,
  onPagination: func.isRequired,
};
