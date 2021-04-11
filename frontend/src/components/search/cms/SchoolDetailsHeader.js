import { object } from 'prop-types';
import styled from 'styled-components';
import Headline from '../../headlines/Headline';
import Children from '../../icons/Children';
import FlexRowStart from '../../structures/_FlexRowStart';
import SchoolRating from './SchoolRating';

export default function SchoolDetailsHeader({ schoolDetails }) {
  const childrenCount = schoolDetails.properties.find((prop) => prop.name === 'Schüler*Innen') || {};
  const teacherCount = schoolDetails.properties.find((prop) => prop.name === 'Lehrer*Innen') || {};

  return (
    <GridHeader>
      <Headline size="s2">{schoolDetails.name}</Headline>
      <SchoolRating />
      <CountContainer>
        <Children />
        <span>{'value' in childrenCount ? childrenCount.value : ''}</span>
        <Children />
        <span>{'value' in teacherCount ? childrenCount.value : ''}</span>
      </CountContainer>
    </GridHeader>
  );
}

const GridHeader = styled.div`
  display: grid;
  grid-template-columns: 2fr min-content;
  grid-gap: 0.1rem 0.8rem;
  margin-bottom: var(--default-padding-s);

  > h1 {
    margin-bottom: 0;
  }
`;

const CountContainer = styled.div`
  ${FlexRowStart}
  align-items: baseline;
  font-size: 0.8rem;

  > span + img {
    margin-left: 0.5rem;
  }

  > img {
    margin-right: 0.1rem;
  }

  > img:first-of-type {
    height: 6px;
  }

  > img:last-of-type {
    height: 10px;
  }
`;

SchoolDetailsHeader.propTypes = {
  schoolDetails: object.isRequired,
};
