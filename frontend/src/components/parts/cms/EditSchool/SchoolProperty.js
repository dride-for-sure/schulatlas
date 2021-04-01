import { func, object } from 'prop-types';
import Input from '../../../form/Input';
import Label from '../../../form/Label';

export default function SchoolProperty({ property, onChange }) {
  return (
    <>
      <Label htmlFor={property.name}>{property.name}</Label>
      <Input
        align="left"
        id={property.name}
        type="text"
        placeholder={`Please enter ${property.name}`}
        value={property.value}
        onChange={onChange} />
    </>
  );
}

SchoolProperty.propTypes = {
  property: object.isRequired,
  onChange: func.isRequired,
};
