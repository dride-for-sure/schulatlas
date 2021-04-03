import { array, func, object, string } from 'prop-types';
import { prettifySlug } from '../../common/slug';
import Input from '../form/Input';
import Label from '../form/Label';
import Select from '../form/Select';
import GridTwoOne from '../grid/cms/GridTwoOne';

export default function Button({ id, component, onChange, pages }) {
  return (
    <>
      <Label key={`${id}-1`}>{component.type}</Label>
      <GridTwoOne key={`${id}-2`}>
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
  id: string.isRequired,
  component: object.isRequired,
  onChange: func.isRequired,
  pages: array.isRequired,
};
