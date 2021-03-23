import { bool, func, string } from 'prop-types';
import styled from 'styled-components';

export default function Input({ center, placeholder, type, value, onChange }) {
  return (
    <Field
      center={center}
      placeholder={placeholder}
      type={type}
      value={value}
      onChange={onChange}
    />
  );
}

const Field = styled.input`
  padding: 0.56rem 0.95rem 0.5rem;
  outline: 0;
  border: 1px solid var(--color-light-silver);
  border-radius: var(--border-radius);
  font-family: var(--font-family-input);
  font-size: var(--font-size-input);
  text-align: center;
`;

Input.propTypes = {
  center: bool,
  placeholder: string.isRequired,
  type: string.isRequired,
  value: string.isRequired,
  onChange: func.isRequired,
};
