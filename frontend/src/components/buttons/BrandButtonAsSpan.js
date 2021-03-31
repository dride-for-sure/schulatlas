import { bool, string } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRight from '../icons/ArrowRight';
import Button from './_Button';

export default function BrandButtonAsSpan({ children, variant, disabled, htmlFor }) {
  return (
    <label htmlFor={htmlFor}>
      <CustomButton variant={variant} disabled={disabled}>
        <span>{children}</span>
        <ArrowRight />
      </CustomButton>
    </label>
  );
}

const CustomButton = styled.span`
  ${Button}

  > img {
    margin: 0.125rem 0 0 0.8rem;
  }
`;

BrandButtonAsSpan.propTypes = {
  children: string.isRequired,
  variant: string,
  disabled: bool,
  htmlFor: string,
};
