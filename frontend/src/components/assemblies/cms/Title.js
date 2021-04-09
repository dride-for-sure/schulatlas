import { func, object } from 'prop-types';
import Input from '../../form/Input';
import Label from '../../form/Label';

export default function Title({ component, onChange }) {
  return (
    <>
      <Label>{component.type}</Label>
      <Input
        align="left"
        id="content"
        type="text"
        placeholder={component.type === 'subtitle' ? 'Add a subtitle...' : 'Add a title...'}
        value={component.content || ''}
        onChange={onChange} />
    </>
  );
}

Title.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
};
