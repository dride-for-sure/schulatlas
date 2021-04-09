import { func, object } from 'prop-types';
import styled, { css } from 'styled-components/macro';
import EnvelopeButton from '../../../buttons/_EnvelopeButton';
import ArrowDown from '../../../icons/ArrowDown';

export default function SchoolListHeader({ toggleSort, searchParams }) {
  const sortIndicator = (sortBy) => searchParams.sort === sortBy && searchParams.direction;

  return (
    <>
      <Button
        onClick={() => toggleSort('number')}
        indicator={sortIndicator('number')}>
        <ArrowDown />
        #
      </Button>
      <Button
        onClick={() => toggleSort('name')}
        indicator={sortIndicator('name')}>
        <ArrowDown />
        Schoolname
      </Button>
      <Button
        onClick={() => toggleSort('city')}
        indicator={sortIndicator('city')}>
        <ArrowDown />
        City
      </Button>
      <Button
        onClick={() => toggleSort('updated')}
        indicator={sortIndicator('updated')}>
        <ArrowDown />
        Updated
      </Button>
      <Button
        onClick={() => toggleSort('markedOutdated')}
        indicator={sortIndicator('markedOutdated')}>
        <ArrowDown />
        Out
      </Button>
    </>
  );
}

const Button = styled.button`
  ${EnvelopeButton}
  font-weight: 600;
  text-align: left;

  // Number
  &:nth-of-type(1) {
    font-style: italic;
  }
  
  // Number, City, Updated & Marked
  &:nth-of-type(1),
  &:nth-of-type(3),
  &:nth-of-type(4),
  &:nth-of-type(5){
    text-align: right;
  }

  > img {
    opacity: 0.15;
    margin: 0 0.3rem 0.05rem 0;
  }

  ${(props) => props.indicator === 'desc' && css`
    > img {
      opacity: 1;
      transform: scaleY(-1);
      -webkit-transform: scaleY(-1);
    }
  `}

  ${(props) => props.indicator === 'asc' && css`
    > img {
      opacity: 1;
    }
  `}
`;

SchoolListHeader.propTypes = {
  toggleSort: func.isRequired,
  searchParams: object.isRequired,
};
