import { bool, string } from 'prop-types';
import styled from 'styled-components/macro';
import ArrowRight from '../icons/ArrowRight';
import Button from './Button';

export default function MainButtonAsSpan({ children, variant, disabled, htmlFor }) {
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
`;

MainButtonAsSpan.propTypes = {
  children: string.isRequired,
  variant: string,
  disabled: bool,
  htmlFor: string,
};
