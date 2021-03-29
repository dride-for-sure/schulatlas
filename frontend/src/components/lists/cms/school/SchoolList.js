import { array } from 'prop-types';
import OrderedList from '../../OrderedList';
import SchoolListItem from './SchoolListItem';

export default function SchoolList({ schools }) {
  return (
    <OrderedList>
      {schools.map((school) => <SchoolListItem key={school.number} school={school} />)}
    </OrderedList>
  );
}

SchoolList.propTypes = {
  schools: array.isRequired,
};
