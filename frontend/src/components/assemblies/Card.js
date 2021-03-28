import { array, func, object } from 'prop-types';
import { prettifySlug } from '../../common/slugHelper';
import Label from '../form/Label';
import Select from '../form/Select';

export default function Card({ component, pages, onChange }) {
  return (
    <>
      <Label>{component.type}</Label>
      <Select
        id="target"
        value={component.target || 'default'}
        onChange={onChange}>
        <option disabled value="default">Please select a page</option>
        {pages && pages.map((page) =>
          <option key={page.slug} value={page.slug}>{prettifySlug(page.slug)}</option>)}
      </Select>
    </>
  );
}

Card.propTypes = {
  component: object.isRequired,
  pages: array.isRequired,
  onChange: func.isRequired,
};
