import { css } from 'styled-components/macro';

const BackgroundCoverCenter = css`
  background-image: url(${(props) => props.background});
  background-repeat: no-repeat;
  background-position: center;
  background-position: 50% 50%;
  background-size: cover;
`;

export default BackgroundCoverCenter;
