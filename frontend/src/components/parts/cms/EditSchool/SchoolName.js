import { func, object } from 'prop-types';
import Input from '../../../form/Input';
import Label from '../../../form/Label';

export default function SchoolName({ school, onChange }) {
  return (
    <>
      <Label htmlFor="name">name</Label>
      <Input
        align="left"
        id="name"
        type="text"
        placeholder="Please enter a school name"
        value={school.name}
        onChange={(event) => onChange(school.id, { name: event.target.value })} />
    </>
  );
}

SchoolName.propTypes = {
  school: object.isRequired,
  onChange: func.isRequired,
};
