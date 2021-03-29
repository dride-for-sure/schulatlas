import { func } from 'prop-types';
import styled from 'styled-components/macro';
import EnvelopeButton from '../../../buttons/_EnvelopeButton';
import GridSchoolList from '../../../grid/cms/_GridSchoolList';

export default function SchoolListHeader({ toggleSort }) {
  return (
    <ListHeader>
      <Button onClick={() => toggleSort('number')}>Number</Button>
      <Button onClick={() => toggleSort('name')}>Schoolname</Button>
      <Button onClick={() => toggleSort('city')}>City</Button>
      <Button onClick={() => toggleSort('updated')}>Updated</Button>
      <Button onClick={() => toggleSort('markedOutdated')}>Out</Button>
    </ListHeader>
  );
}

const ListHeader = styled.li`
  ${GridSchoolList}
`;

const Button = styled.button`
  ${EnvelopeButton}
  font-weight: 600;
  text-align: left;
  
  &:nth-of-type(3),
  &:nth-of-type(4),
  &:nth-of-type(5){
    text-align: right;
  }
`;

SchoolListHeader.propTypes = {
  toggleSort: func.isRequired,
};
