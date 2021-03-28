import { bool, string } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRight from '../icons/ArrowRight';
import Button from './Button';

export default function MainButtonAsSpan({ children, variant, disabled }) {
  return (
    <CustomButton variant={variant} disabled={disabled}>
      <span>{children}</span>
      <ArrowRight />
    </CustomButton>
  );
}

const CustomButton = styled.span`
  ${Button}
`;

MainButtonAsSpan.propTypes = {
  children: string.isRequired,
  variant: string,
  disabled: bool,
};
