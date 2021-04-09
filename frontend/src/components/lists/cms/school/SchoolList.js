import { func, object, string } from 'prop-types';
import Headline from '../../../headlines/Headline';
import GridSchoolList from '../../../structures/GridSchoolList';
import Pagination from './Pagination';
import SchoolListHeader from './SchoolListHeader';
import SchoolListItem from './SchoolListItem';

export default function SchoolList({ prefix, schools, onPagination, toggleSort, searchParams }) {
  return (
    <GridSchoolList>
      <Headline size="l" before="Search">{prefix || 'All Types'}</Headline>
      <SchoolListHeader toggleSort={toggleSort} searchParams={searchParams} />
      {schools.content.map((school) => <SchoolListItem key={school.number} school={school} />)}
      <Pagination pageable={schools} onPagination={onPagination} />
    </GridSchoolList>
  );
}

SchoolList.propTypes = {
  prefix: string.isRequired,
  schools: object.isRequired,
  toggleSort: func.isRequired,
  onPagination: func.isRequired,
  searchParams: object.isRequired,
};
