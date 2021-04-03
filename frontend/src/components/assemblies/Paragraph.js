import { func, object, string } from 'prop-types';
import Label from '../form/Label';
import Textarea from '../form/Textarea';

export default function Paragraph({ id, component, onChange }) {
  return (
    <>
      <Label key={`${id}-1`}>{component.type}</Label>
      <Textarea
        key={`${id}-2`}
        id="content"
        placeholder="Add some content..."
        value={component.content || ''}
        onChange={onChange} />
    </>
  );
}

Paragraph.propTypes = {
  id: string.isRequired,
  component: object.isRequired,
  onChange: func.isRequired,
};
