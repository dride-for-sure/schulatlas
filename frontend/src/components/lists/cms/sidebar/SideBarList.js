import { array, func } from 'prop-types';
import UnorderedList from '../../UnorderedList';
import SideBarListItem from './SideBarListItem';

export default function SideBarList({ pages, types, setLandingPage }) {
  return (
    <UnorderedList>
      {pages && pages.map((page) =>
        <SideBarListItem key={page.slug} page={page} setLandingPage={setLandingPage} />)}
      {types && types.map((type) =>
        <SideBarListItem key={type.slug} type={type} />)}
    </UnorderedList>
  );
}

SideBarList.propTypes = {
  pages: array,
  types: array,
  setLandingPage: func,
};
