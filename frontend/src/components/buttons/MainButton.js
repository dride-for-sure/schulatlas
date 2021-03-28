import { bool, func, string } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRight from '../icons/ArrowRight';
import Button from './Button';

export default function MainButton({ children, variant, disabled, type, onClick }) {
  return (
    <CustomButton type={type || 'submit'} variant={variant} disabled={disabled} onClick={onClick}>
      <span>{children}</span>
      <ArrowRight />
    </CustomButton>
  );
}

const CustomButton = styled.button`
  ${Button};
`;

MainButton.propTypes = {
  children: string.isRequired,
  variant: string,
  disabled: bool,
  type: string,
  onClick: func,
};
