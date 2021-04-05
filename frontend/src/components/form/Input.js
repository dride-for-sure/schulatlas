import styled from 'styled-components/macro';

const Input = styled.input`
  padding: 0.47rem 0.95rem 0.38rem;
  outline: 0;
  border: 1px solid var(--color-light-silver);
  border-radius: var(--border-radius);
  font-family: var(--font-family-input);
  font-size: var(--font-size-input);
  text-align: ${(props) => (props.align || 'center')};
  width: 100%;
  height: fit-content;
  min-height: 32px;
  box-sizing: border-box;
  
  &[type=number]::-webkit-inner-spin-button,
  &[type=number]::-webkit-outer-spin-button { 
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    margin: 0;
  }
`;

export default Input;
