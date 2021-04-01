import { object } from 'prop-types';
import styled from 'styled-components/macro';
import convertTimeStampToDate from '../../../../common/timeStamp';
import Label from '../../../form/Label';

export default function SchoolSummary({ school }) {
  return (
    <>
      <Label>Summary</Label>
      <Container>
        <span>
          <b>Number:</b>
          {school.number}
        </span>
        <span>
          <b>MarkedOutdated:</b>
          {school.markedOutdated}
        </span>
        <span>
          <b>Updated:</b>
          {convertTimeStampToDate(school.updated)}
          {' '}
          <b>by:</b>
          {school.userId}
        </span>
      </Container>
    </>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: calc(var(--container-padding) * 0.5) var(--container-padding);
  margin-top: 0.45rem;
  
  > span {
    white-space: nowrap;
    
    > b {
      margin-right: 0.3rem;
    }
  }

`;

SchoolSummary.propTypes = {
  school: object.isRequired,
};
