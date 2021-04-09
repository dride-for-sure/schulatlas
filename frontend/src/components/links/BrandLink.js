import { string } from 'prop-types';
import { Link } from 'react-router-dom';
import styled from 'styled-components/macro';
import Button from '../buttons/_Button';
import ArrowRight from '../icons/ArrowRight';

export default function BrandLink({ children, variant, url }) {
  return (
    <CustomLink to={url} variant={variant}>
      <span>{children}</span>
      <ArrowRight />
    </CustomLink>
  );
}

const CustomLink = styled(Link)`
  ${Button};
  text-decoration: none;

  > img {
    margin: 0.125rem 0 0 0.8rem;
  }
`;

BrandLink.propTypes = {
  children: string.isRequired,
  variant: string,
  url: string.isRequired,
};
