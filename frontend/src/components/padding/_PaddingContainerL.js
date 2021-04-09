import { css } from 'styled-components/macro';
import minWidth from '../../config/deviceBreakpoint';

const PaddingContainerL = css`
  padding: var(--default-padding-m2) var(--default-padding-s);

  @media ${minWidth.s} {
    padding: var(--default-padding-l2) var(--default-padding-m);
  }

  @media ${minWidth.l} {
    padding: var(--default-padding-xl) var(--default-padding-m);
  }
`;

export default PaddingContainerL;
