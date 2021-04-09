import styled from 'styled-components/macro';

const Textarea = styled.textarea`
  padding: 0.45rem 0.95rem 0.4rem;
  outline: 0;
  border: 1px solid var(--color-lighter-silver);
  border-radius: var(--border-radius);
  font-family: var(--font-family-input);
  font-size: var(--font-size-input);
  text-align: left;
  width: 100%;
  height: 200px;
  box-sizing: border-box;
  white-space: normal;
`;

export default Textarea;
