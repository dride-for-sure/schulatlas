import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';

const Paragraph2Columns = styled.p`
  line-height: var(--line-height);
  text-align: justify;
  
  @media ${minWidth.s} {
    text-align: left;
  }

  @media ${minWidth.m} {
    column-count: 2;
    column-gap: var(--default-padding-l);
  }

`;

export default Paragraph2Columns;
