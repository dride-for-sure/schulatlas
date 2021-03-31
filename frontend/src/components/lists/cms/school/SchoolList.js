import { func, object, string } from 'prop-types';
import GridSchoolList from '../../../grid/cms/GridSchoolList';
import Headline from '../../../headlines/Headline';
import Pagination from './Pagination';
import SchoolListHeader from './SchoolListHeader';
import SchoolListItem from './SchoolListItem';

export default function SchoolList({ type, schools, onPagination, toggleSort, searchParams }) {
  return (
    <GridSchoolList>
      <Headline size="l">
        Filter:
        {' '}
        <i>{type || 'All Types'}</i>
      </Headline>
      <SchoolListHeader toggleSort={toggleSort} searchParams={searchParams} />
      {schools.content.map((school) => <SchoolListItem key={school.number} school={school} />)}
      <Pagination pageable={schools} onPagination={onPagination} />
    </GridSchoolList>
  );
}

SchoolList.propTypes = {
  type: string.isRequired,
  schools: object.isRequired,
  toggleSort: func.isRequired,
  onPagination: func.isRequired,
  searchParams: object.isRequired,
};
