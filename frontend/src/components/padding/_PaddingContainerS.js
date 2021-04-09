import { css } from 'styled-components/macro';
import minWidth from '../../config/deviceBreakpoint';

const PaddingContainerS = css`
  padding: var(--default-padding-s) var(--default-padding-s);

  @media ${minWidth.s} {
    padding: var(--default-padding-s) var(--default-padding-m);
  }
`;

export default PaddingContainerS;
