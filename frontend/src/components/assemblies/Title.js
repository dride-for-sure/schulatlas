import { func, object, string } from 'prop-types';
import Input from '../form/Input';
import Label from '../form/Label';

export default function Title({ id, component, onChange }) {
  return (
    <>
      <Label key={`${id}-1`}>{component.type === 'title' ? 'title' : 'subtitle'}</Label>
      <Input
        key={`${id}-2`}
        align="left"
        id="content"
        type="text"
        placeholder={component.type === 'title' ? 'Enter a title...' : 'Enter a subtitle...'}
        value={component.content || ''}
        onChange={onChange} />
    </>
  );
}

Title.propTypes = {
  id: string.isRequired,
  component: object.isRequired,
  onChange: func.isRequired,
};
