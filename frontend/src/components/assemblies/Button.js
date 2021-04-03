import { array, func, object } from 'prop-types';
import { prettifySlug } from '../../common/slug';
import Input from '../form/Input';
import Label from '../form/Label';
import Select from '../form/Select';
import GridTwoOne from '../structures/GridTwoOne';

export default function Button({ component, onChange, pages }) {
  return (
    <>
      <Label>{component.type}</Label>
      <GridTwoOne>
        <Input
          align="left"
          id="content"
          type="text"
          placeholder="Button Text..."
          value={component.content || ''}
          onChange={onChange} />
        <Select
          id="target"
          value={component.target || 'default'}
          onChange={onChange}>
          <option disabled value="default">Please select a page</option>
          {pages && pages.map((page) =>
            <option key={page.slug} value={page.slug}>{prettifySlug(page.slug)}</option>)}
        </Select>
      </GridTwoOne>
    </>
  );
}

Button.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
  pages: array.isRequired,
};
