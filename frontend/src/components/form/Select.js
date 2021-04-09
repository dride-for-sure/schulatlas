import styled from 'styled-components/macro';
import Arrow from '../../resources/images/ArrowDownDark.png';

const Select = styled.select`
  width: 180px;
  height: fit-content;
  padding: 0.45rem 2.2rem 0.4rem 0.95rem;
  outline: 0;
  border: 1px solid var(--color-lighter-silver);
  border-radius: var(--border-radius);
  font-family: var(--font-family-input);
  font-size: var(--font-size-input);
  text-align: ${(props) => (props.align || 'center')};
  text-transform: capitalize;
  -moz-appearance: none; 
  -webkit-appearance: none; 
  appearance: none;
  background: url(${Arrow});
  background-repeat: no-repeat;
  background-size: 10px;
  background-position: 90% center;
`;

export default Select;
