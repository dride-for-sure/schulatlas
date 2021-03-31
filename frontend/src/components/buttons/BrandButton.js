import { bool, func, string } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRight from '../icons/ArrowRight';
import Button from './_Button';

export default function BrandButton({ children, variant, disabled, type, onClick }) {
  return (
    <CustomButton type={type || 'submit'} variant={variant} disabled={disabled} onClick={onClick}>
      <span>{children}</span>
      <ArrowRight />
    </CustomButton>
  );
}

const CustomButton = styled.button`
  ${Button};

  > img {
    margin: 0.125rem 0 0 0.8rem;
  }
`;

BrandButton.propTypes = {
  children: string.isRequired,
  variant: string,
  disabled: bool,
  type: string,
  onClick: func,
};
