import { array, func, object } from 'prop-types';
import prettifySlug from '../../common/prettifySlug';
import Input from '../form/Input';
import Label from '../form/Label';
import Select from '../form/Select';
import GridOneTwo from '../grid/cms/GridOneTwo';

export default function Button({ component, onChange, pages }) {
  return (
    <>
      <Label>{component.type}</Label>
      <GridOneTwo>
        <Input
          align="left"
          id="content"
          type="text"
          placeholder="Button Text..."
          value={component.content}
          onChange={onChange} />
        <Select
          id="target"
          value={component.target || ''}
          onChange={onChange}>
          {pages && pages.map((page) =>
            <option key={page.slug} value={page.slug}>{prettifySlug(page.slug)}</option>)}
        </Select>
      </GridOneTwo>
    </>
  );
}

Button.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
  pages: array.isRequired,
};
