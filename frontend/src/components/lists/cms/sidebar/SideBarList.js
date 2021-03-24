import { array } from 'prop-types';
import UnorderedList from '../../UnorderedList';
import SideBarListItem from './SideBarListItem';

export default function SideBarList({ pages, types }) {
  return (
    <UnorderedList>
      {pages && pages.map((page) =>
        <SideBarListItem key={page.name} page={page} />)}
      {types && types.map((type) =>
        <SideBarListItem key={type.name} type={type} />)}
    </UnorderedList>
  );
}

SideBarList.propTypes = {
  pages: array,
  types: array,
};
