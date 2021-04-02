import { func, object } from 'prop-types';
import Input from '../../../form/Input';
import Label from '../../../form/Label';

export default function SchoolNumber({ school, onChange }) {
  return (
    <>
      <Label htmlFor="number">Number</Label>
      <Input
        align="left"
        id="number"
        type="text"
        placeholder="Please enter a school number"
        value={school.number}
        onChange={(event) => onChange(school.id, { number: event.target.value })} />
    </>
  );
}

SchoolNumber.propTypes = {
  school: object.isRequired,
  onChange: func.isRequired,
};
