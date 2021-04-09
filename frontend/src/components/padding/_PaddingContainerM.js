import { css } from 'styled-components/macro';
import minWidth from '../../config/deviceBreakpoint';

const PaddingContainerM = css`
  padding: var(--default-padding-s2) var(--default-padding-s);

  @media ${minWidth.s} {
    padding: var(--default-padding-l) var(--default-padding-m);
  }
`;

export default PaddingContainerM;
