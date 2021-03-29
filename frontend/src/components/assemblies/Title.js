import { func, object } from 'prop-types';
import Input from '../form/Input';
import Label from '../form/Label';

export default function Title({ component, onChange }) {
  return (
    <>
      <Label>{component.type === 'title' ? 'title' : 'subtitle'}</Label>
      <Input
        align="left"
        id="content"
        type="text"
        placeholder={component.type === 'title' ? 'Enter a title...' : 'Enter a subtitle...'}
        value={component.content}
        onChange={onChange} />
    </>
  );
}

Title.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
};
