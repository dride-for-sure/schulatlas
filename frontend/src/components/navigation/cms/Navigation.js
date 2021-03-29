import styled from 'styled-components/macro';
import NavigationLink from '../../links/NavigationLink';
import OrderedList from '../../lists/_OrderedList';
import FlexRowEnd from '../../structures/_FlexRowEnd';

export default function Navigation() {
  return (
    <List>
      <li><NavigationLink to="/cms/pages">Pages</NavigationLink></li>
      <li><NavigationLink to="/cms/schools">Schools</NavigationLink></li>
    </List>
  );
}

const List = styled.ol`
  ${OrderedList}
  ${FlexRowEnd}
  color: white;
  font-family: var(--font-family-navigation);
  font-size: var(--font-size-navigation);
  letter-spacing: var(--letter-spacing);

  > li {
    align-self: center;
  }

  > li+li {
    margin-left: 2rem;
  }
`;
