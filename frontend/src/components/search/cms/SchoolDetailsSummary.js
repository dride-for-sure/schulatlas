import { object } from 'prop-types';
import styled from 'styled-components';
import FlexColumnStart from '../../structures/_FlexColumnStart';

export default function SchoolDetailsSummary({ schoolDetails }) {
  return (
    <Container>
      <Flex>
        <span><b>{schoolDetails.type}</b></span>
        <span>{`${schoolDetails.address.street} ${schoolDetails.address.number}`}</span>
        <span>{`${schoolDetails.address.postcode} ${schoolDetails.address.city}`}</span>
      </Flex>
      <Flex>
        <span>{schoolDetails.contact.phone ? schoolDetails.contact.phone : 'Phone not available'}</span>
        <span>{schoolDetails.contact.email ? schoolDetails.contact.email : 'Email not available'}</span>
        <span>{schoolDetails.contact.url ? schoolDetails.contact.url : 'Url not available'}</span>
      </Flex>
    </Container>
  );
}

const Container = styled.div`
  > div + div {
    margin: var(--default-padding-s) 0;
  }
`;

const Flex = styled.div`
  ${FlexColumnStart}

  > span + span {
    margin-top: calc(var(--default-margin) * 0.1)
  }
  
`;
SchoolDetailsSummary.propTypes = {
  schoolDetails: object.isRequired,
};
