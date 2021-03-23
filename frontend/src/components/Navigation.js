import styled from 'styled-components/macro';
import NavigationLink from './links/NavigationLink';

export default function Navigation() {
  return (
    <>
      <List>
        <li><NavigationLink to="/cms">Landing Page</NavigationLink></li>
        <li><NavigationLink to="/cms/pages">Pages</NavigationLink></li>
        <li><NavigationLink to="/cms/schools">Schools</NavigationLink></li>
      </List>
    </>
  );
}

const List = styled.ol`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  list-style: none;
  
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
