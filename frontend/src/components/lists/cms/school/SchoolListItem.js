import { object } from 'prop-types';
import styled, { css } from 'styled-components/macro';
import convertTimeStampToDate from '../../../../common/timeStamp';
import GridSchoolList from '../../../grid/cms/_GridSchoolList';
import RegularLink from '../../../links/RegularLink';

export default function SchoolListItem({ school }) {
  return (
    <ListItem markedOutdated={school.markedOutdated}>
      <RegularLink to={`/cms/school/${school.number}`}>
        <span>{school.number}</span>
        <span>{school.name}</span>
        <span>{school.address.city}</span>
        <span>{convertTimeStampToDate(school.updated)}</span>
        <span>{school.markedOutdated}</span>
      </RegularLink>
    </ListItem>
  );
}

const ListItem = styled.li`

  ${(props) => props.markedOutdated >= 5 && css`
    color: var(--color-paradise-pink);
    font-weight: 600;
  `}
  
  > a {
    ${GridSchoolList};
  }
`;

SchoolListItem.propTypes = {
  school: object.isRequired,
};
