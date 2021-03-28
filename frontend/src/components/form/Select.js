import styled from 'styled-components/macro';
import arrow from '../../resources/images/ArrowDownDark.png';

const Select = styled.select`
  width: 180px;
  padding: 0.45rem 0.95rem 0.4rem;
  outline: 0;
  border: 1px solid var(--color-light-silver);
  border-radius: var(--border-radius);
  font-family: var(--font-family-input);
  font-size: var(--font-size-input);
  text-align: ${(props) => (props.align || 'center')};
  text-transform: capitalize;
  -moz-appearance: none; 
  -webkit-appearance: none; 
  appearance: none;
  background: url(${arrow});
  background-repeat: no-repeat;
  background-size: 10px;
  background-position: 90% center;
`;

export default Select;
