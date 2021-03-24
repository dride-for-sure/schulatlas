import { array } from 'prop-types';
import H1 from '../../../headlines/H1';
import OrderedList from '../../OrderedList';
import SideBarListItem from './SideBarListItem';

export default function SideBar({ pages, types }) {
  return (
    <>
      <H1>{pages ? 'Pages' : 'Schools'}</H1>
      <OrderedList>
        {pages && pages.map((page) =>
          <SideBarListItem key={page.name} page={page} />)}
        {types && types.map((type) =>
          <SideBarListItem key={type.name} type={type} />)}
      </OrderedList>
    </>
  );
}

SideBar.propTypes = {
  pages: array,
  types: array,
};
