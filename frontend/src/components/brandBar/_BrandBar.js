import { css } from 'styled-components/macro';

const BrandBar = css`
    position: absolute;
    display: block;
    width: 100%;
    height: 4px;
    content: '';
    background: var(--gradient-primary);
    z-index: 2;
`;

export default BrandBar;
