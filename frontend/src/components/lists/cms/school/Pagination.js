import { func, object } from 'prop-types';
import styled from 'styled-components/macro';
import EnvelopeButton from '../../../buttons/_EnvelopeButton';
import ArrowLeft from '../../../icons/ArrowLeft';
import ArrowRight from '../../../icons/ArrowRight';

export default function Pagination({ pageable, onPagination }) {
  return (
    <AlignRight>
      {!pageable.first && (
        <Button type="button" onClick={() => onPagination('last')}>
          <ArrowLeft dark />
        </Button>
      )}
      {!pageable.last && (
        <Button type="button" onClick={() => onPagination('next')}>
          <ArrowRight dark />
        </Button>
      )}
    </AlignRight>
  );
}

const AlignRight = styled.div`
  margin-top: var(--container-padding);
  text-align: right;

  > button + button {
    margin-left: 1rem;
  }

  > button:last-of-type {
    padding-right: 0;
  }
`;

const Button = styled.button`
  ${EnvelopeButton}
  padding: 0.3rem 0.5rem;
`;

Pagination.propTypes = {
  pageable: object.isRequired,
  onPagination: func.isRequired,
};
