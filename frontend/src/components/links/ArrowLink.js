import { string } from 'prop-types';
import { Link } from 'react-router-dom';
import styled from 'styled-components/macro';
import EnvelopeButton from '../buttons/_EnvelopeButton';
import ArrowRight from '../icons/ArrowRight';

export default function ArrowLink({ to, alt }) {
  return (
    <CustomLink to={to} alt={alt}>
      <ArrowRight dark />
    </CustomLink>
  );
}

const CustomLink = styled(Link)`
  ${EnvelopeButton}
  margin-right: .4rem;
`;

ArrowLink.propTypes = {
  to: string.isRequired,
  alt: string.isRequired,
};
