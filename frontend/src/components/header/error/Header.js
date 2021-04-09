import styled from 'styled-components/macro';
import Logo from '../../icons/Logo';
import Navigation from '../../navigation/schulatlas/Navigation';
import PaddingContainerS from '../../padding/_PaddingContainerS';
import FlexRowCenter from '../../structures/_FlexRowCenter';
import FlexRowSpaceBetween from '../../structures/_FlexRowSpaceBetween';
import MaxWidthL from '../../structures/_MaxWidthL';

export default function Header() {
  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Logo />
          <Navigation />
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.header``;

const PaddingContainer = styled.div`
  ${FlexRowCenter};
  ${PaddingContainerS}
`;

const MaxWidthContainer = styled.nav`
  ${FlexRowSpaceBetween}
  ${MaxWidthL}
`;
