import { func, object } from 'prop-types';
import { getAssemblyVariants } from '../../config/schulatlasConfig';
import Label from '../form/Label';
import Select from '../form/Select';

export default function Variant({ assembly, onChange }) {
  const variants = getAssemblyVariants(assembly.type);
  const handleChange = (event) => {
    onChange(assembly.id, { variant: event.target.value });
  };
  return (
    <>
      <Label>Variants</Label>
      <Select
        id="target"
        value={assembly.variant || 'default'}
        onChange={handleChange}>
        <option disabled value="default">Please select a variant</option>
        {variants && variants.map((variant) =>
          <option key={variant} value={variant}>{variant}</option>)}
      </Select>
    </>
  );
}

Variant.propTypes = {
  assembly: object.isRequired,
  onChange: func.isRequired,
};
