import { string } from 'prop-types';
import styled from 'styled-components/macro';
import CapitalizeFirstLetter from '../text/_CapitalizeFirstLetter';
import TextEllipsis from '../text/_TextEllipsis';

const HeadlineTwoRowWithSubtitle = ({ row1, row2, subtitle }) => (
  <Container>
    <Row1>{row1}</Row1>
    <Row2>{row2}</Row2>
    <Subtitle>{subtitle}</Subtitle>
  </Container>
);

const Container = styled.div`
  margin-bottom: 2rem;
  
  > * {
    ${CapitalizeFirstLetter}
    ${TextEllipsis}
    padding: 0 0.5rem;
    border-radius: calc(var(--border-radius) * 0.6);
    width: fit-content;
  }
`;

const Row1 = styled.h2`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-l);
  background-color: white;
`;

const Row2 = styled.h3`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-l);
  font-style: var(--font-style-title);
  font-weight: var(--font-weight-title);
  background-color: white;
  margin-top: 0.5rem;
`;

const Subtitle = styled.h4`
  color: white;
  font-family: var(--font-family-subtitle);
  font-size: var(--font-size-subtitle-l);
  font-style: var(--font-style-subtitle);
  font-weight: var(--font-weight-subtitle);
  margin-top: 0.8rem;
`;

HeadlineTwoRowWithSubtitle.propTypes = {
  row1: string.isRequired,
  row2: string.isRequired,
  subtitle: string.isRequired,
};

export default HeadlineTwoRowWithSubtitle;
