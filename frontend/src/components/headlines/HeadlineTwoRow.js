import { string } from 'prop-types';
import styled from 'styled-components/macro';
import CapitalizeFirstLetter from '../text/_CapitalizeFirstLetter';
import TextEllipsis from '../text/_TextEllipsis';

const HeadlineTwoRow = ({ row1, row2 }) => (
  <Container>
    <Row1>{row1}</Row1>
    <Row2>{row2}</Row2>
  </Container>
);

const Container = styled.div`
  > * {
    ${CapitalizeFirstLetter}
    ${TextEllipsis}
    background-color: white;
    border-radius: calc(var(--border-radius) * 0.6);
    padding: 0 0.5rem;
    width: fit-content;
  }
`;

const Row1 = styled.h2`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-l);
`;

const Row2 = styled.h3`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-l);
  font-style: var(--font-style-title);
  font-weight: var(--font-weight-title);
  margin-top: 0.5rem;
`;

HeadlineTwoRow.propTypes = {
  row1: string.isRequired,
  row2: string.isRequired,
};

export default HeadlineTwoRow;
