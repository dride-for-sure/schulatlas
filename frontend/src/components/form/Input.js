import styled from 'styled-components/macro';

const Input = styled.input`
  padding: 0.45rem 0.95rem 0.4rem;
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
`;

export default Input;
