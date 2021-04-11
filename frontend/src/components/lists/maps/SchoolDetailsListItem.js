import { object } from 'prop-types';
import styled from 'styled-components';

export default function SchoolDetailsListItem({ property }) {
  const getUnit = () => {
    switch (property.unit) {
      case '%':
        return '%';
      case 'Mbit':
        return 'Mbit';
      default:
        return '';
    }
  };
  return (
    <Container>
      <b>{`${property.name}:  `}</b>
      {`${property.value}${getUnit()}`}
    </Container>
  );
}

const Container = styled.li``;

SchoolDetailsListItem.propTypes = {
  property: object.isRequired,
};
