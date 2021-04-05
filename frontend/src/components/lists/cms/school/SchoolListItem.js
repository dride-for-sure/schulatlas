import { object } from 'prop-types';
import styled, { css } from 'styled-components/macro';
import convertTimeStampToDate from '../../../../common/timeStamp';
import RegularLink from '../../../links/RegularLink';

export default function SchoolListItem({ school }) {
  return (
    <>
      <RegularLink to={`/cms/school/${school.number}`}>
        <AlignRightItalic markedOutdated={school.markedOutdated}>
          {school.number}
        </AlignRightItalic>
      </RegularLink>
      <RegularLink to={`/cms/school/${school.number}`}>
        <AlignLeft markedOutdated={school.markedOutdated}>
          {school.name}
        </AlignLeft>
      </RegularLink>
      <RegularLink to={`/cms/school/${school.number}`}>
        <AlignRight markedOutdated={school.markedOutdated}>
          {school.address.city}
        </AlignRight>
      </RegularLink>
      <RegularLink to={`/cms/school/${school.number}`}>
        <AlignRight markedOutdated={school.markedOutdated}>
          {convertTimeStampToDate(school.updated)}
        </AlignRight>
      </RegularLink>
      <RegularLink to={`/cms/school/${school.number}`}>
        <AlignRight markedOutdated={school.markedOutdated}>
          {school.markedOutdated}
        </AlignRight>
      </RegularLink>
    </>
  );
}

const ListItem = css`
  ${(props) => props.markedOutdated >= 5 && css`
    color: var(--color-paradise-pink);
    font-weight: 600;
  `}
`;

const AlignLeft = styled.span`
  ${ListItem};
`;

const AlignRight = styled.span`
  ${ListItem};
  display:block;
  text-align: right;
`;

const AlignRightItalic = styled.span`
  ${ListItem};
  display:block;
  text-align: right;
  font-style: italic;
  text-overflow: ellipsis;
  overflow: hidden;
`;

SchoolListItem.propTypes = {
  school: object.isRequired,
};
