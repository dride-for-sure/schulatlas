import styled from 'styled-components/macro';
import minWidth from '../../../../config/deviceBreakpoint';
import Logo from '../../../icons/Logo';
import RegularLink from '../../../links/RegularLink';
import UnorderedList from '../../../lists/_UnorderedList';
import PaddingContainerL from '../../../padding/_PaddingContainerL';
import FlexColumnCenter from '../../../structures/_FlexColumnCenter';
import FlexRowCenter from '../../../structures/_FlexRowCenter';
import FlexRowSpaceBetween from '../../../structures/_FlexRowSpaceBetween';
import MaxWidthM from '../../../structures/_MaxWidthM';
import TextEllipsis from '../../../text/_TextEllipsis';

export default function Footer() {
  return (
    <Wrapper>
      <PaddingContainer>
        <MaxWidthContainer>
          <Logo />
          <List>
            <li><RegularLink>Open Data</RegularLink></li>
            <li><RegularLink>Open Knowledge Foundation</RegularLink></li>
            <li><RegularLink>PrototypeFund</RegularLink></li>
          </List>
          <List>
            <li><RegularLink>Dokumentation</RegularLink></li>
            <li><RegularLink>API Zugang</RegularLink></li>
            <li><RegularLink>Unterstützung</RegularLink></li>
            <li><RegularLink>Sourcecode</RegularLink></li>
            <li><RegularLink to="/cms">CMS Login</RegularLink></li>
          </List>
          <List>
            <li><RegularLink>Nutzungsbedingungen</RegularLink></li>
            <li><RegularLink>Datenschutz</RegularLink></li>
            <li><RegularLink>Kontakt</RegularLink></li>
            <li><RegularLink>Impressum</RegularLink></li>
          </List>
        </MaxWidthContainer>
      </PaddingContainer>
    </Wrapper>
  );
}

const Wrapper = styled.footer`
  background-color: var(--color-medium-silver);
`;

const PaddingContainer = styled.div`
  ${FlexRowCenter}
  ${PaddingContainerL}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthM}
  ${FlexColumnCenter}
  align-items: center;
  color: white;
  text-align: center;

  ul {
    margin-top: var(--default-padding-m2);
  }

  @media ${minWidth.m} {
    ${FlexRowSpaceBetween}
    align-items: flex-start;
    text-align: left;

    ul {
      margin-top: 0;
    }
  }
`;

const List = styled.ul`
  ${UnorderedList}

  > li {
    ${TextEllipsis}
  }
`;
