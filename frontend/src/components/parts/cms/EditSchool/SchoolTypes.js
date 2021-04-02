import { array, func, object } from 'prop-types';
import Label from '../../../form/Label';
import Select from '../../../form/Select';

export default function SchoolType({ school, availableTypes, onChange }) {
  return (
    <>
      <Label htmlFor="type">Type</Label>
      <Select
        id="type"
        value={school.type || 'default'}
        onChange={(event) => onChange(school.id, { type: event.target.value })}>
        <option disabled value="default">Please select a type</option>
        {availableTypes && availableTypes.map((type) =>
          <option key={type.name} value={type.name}>{type.name}</option>)}
      </Select>
    </>
  );
}

SchoolType.propTypes = {
  school: object.isRequired,
  availableTypes: array.isRequired,
  onChange: func.isRequired,
};
