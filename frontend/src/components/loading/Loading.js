import styled from 'styled-components/macro';
import Headline from '../headlines/Headline';
import FlexColumnCenter from '../structures/_FlexColumnCenter';

export default function Loading() {
  return (
    <Wrapper>
      <Headline size="l">Es geht gleich los!</Headline>
      <Loader />
    </Wrapper>
  );
}

const Wrapper = styled.main`
  ${FlexColumnCenter}
  align-items: center;
  height: 100vh;
  width: 100vw;
`;

const Loader = styled.div`
  border: 2px solid var(--color-lightest-silver);
  border-top: 2px solid var(--color-paradise-pink);
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 3s linear infinite;
  margin-top: 1rem;

  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
`;
