import { array, func, object } from 'prop-types';
import DeleteButton from '../../../buttons/DeleteButton';
import Input from '../../../form/Input';
import Label from '../../../form/Label';
import Select from '../../../form/Select';
import GridMaxOne from '../../../structures/GridMaxOne';

export default function SchoolProperty({
  property,
  onDeleteProperty,
  availableProperties,
  onChange }) {
  const handlePropertySelection = (event) => {
    const chosenProp = availableProperties.find(
      (availableProperty) => availableProperty.name === event.target.value,
    );
    if (chosenProp) {
      onChange(property.id, { name: event.target.value, unit: chosenProp.unit, value: '' });
    }
  };

  if (property.name) {
    return (
      <>
        <Label htmlFor={property.name}>{property.name}</Label>
        <GridMaxOne>
          <Input
            align="left"
            id={property.name}
            type="text"
            placeholder={`Please enter ${property.name}`}
            value={property.value}
            onChange={(event) => onChange(property.id, { value: event.target.value })} />
          <DeleteButton onClick={() => onDeleteProperty(property.id)} />
        </GridMaxOne>
      </>
    );
  }
  return (
    <>
      <Select
        id="name"
        value={property.name || 'default'}
        onChange={handlePropertySelection}>
        <option disabled value="default">Select property</option>
        {availableProperties && availableProperties.map((availableProperty) => (
          <option
            key={availableProperty.name}
            value={availableProperty.name}>
            {availableProperty.name}
          </option>
        ))}
      </Select>
    </>
  );
}

SchoolProperty.propTypes = {
  property: object.isRequired,
  onDeleteProperty: func.isRequired,
  availableProperties: array.isRequired,
  onChange: func.isRequired,
};
