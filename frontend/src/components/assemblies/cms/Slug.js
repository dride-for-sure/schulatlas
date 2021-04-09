import { func, string } from 'prop-types';
import Input from '../../form/Input';
import Label from '../../form/Label';

export default function Slug({ slug, onChange }) {
  return (
    <>
      <Label>Slug</Label>
      <Input
        align="left"
        id="slug"
        type="text"
        placeholder="Add a page slug"
        value={slug || ''}
        onChange={onChange} />
    </>
  );
}

Slug.propTypes = {
  slug: string.isRequired,
  onChange: func.isRequired,
};
