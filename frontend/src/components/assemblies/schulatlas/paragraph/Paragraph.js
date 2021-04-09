import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';

const Paragraph = styled.p`
  line-height: var(--line-height);
  text-align: justify;

  @media ${minWidth.m} {
    text-align: left;
  }
`;

export default Paragraph;
