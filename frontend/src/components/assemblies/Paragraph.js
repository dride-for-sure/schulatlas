import { func, object } from 'prop-types';
import Label from '../form/Label';
import Textarea from '../form/Textarea';

export default function Paragraph({ component, onChange }) {
  return (
    <>
      <Label>{component.type}</Label>
      <Textarea
        id="content"
        placeholder="Add some content..."
        value={component.content || ''}
        onChange={onChange} />
    </>
  );
}

Paragraph.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
};
