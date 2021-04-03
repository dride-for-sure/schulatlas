import { array, func, object, string } from 'prop-types';
import { prettifySlug } from '../../common/slug';
import Label from '../form/Label';
import Select from '../form/Select';

export default function Card({ id, component, pages, onChange }) {
  return (
    <>
      <Label key={`${id}-1`}>{component.type}</Label>
      <Select
        key={`${id}-2`}
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
  id: string.isRequired,
  component: object.isRequired,
  pages: array.isRequired,
  onChange: func.isRequired,
};
