import { func, object } from 'prop-types';
import Label from '../../form/Label';
import Textarea from '../../form/Textarea';

export default function Paragraph({ component, onChange }) {
  return (
    <>
      <Label>{component.type}</Label>
      <Textarea
        id="content"
        placeholder={component.type === 'paragraph' ? 'Add some content...' : 'Add a description...'}
        value={component.content || ''}
        onChange={onChange} />
    </>
  );
}

Paragraph.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
};
