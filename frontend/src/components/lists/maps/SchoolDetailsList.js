import { array } from 'prop-types';
import styled from 'styled-components';
import UnorderedList from '../_UnorderedList';
import SchoolDetailsListItem from './SchoolDetailsListItem';

export default function SchoolDetailsList({ schoolDetails }) {
  return (
    <List>
      {schoolDetails.properties.map((property) =>
        <SchoolDetailsListItem key={property.name} property={property} />)}
    </List>
  );
}

const List = styled.ul`
  ${UnorderedList};

  > li {
    height: fit-content;
  }
`;

SchoolDetailsList.propTypes = {
  schoolDetails: array.isRequired,
};
