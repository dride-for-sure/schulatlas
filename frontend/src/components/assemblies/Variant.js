import { func, object } from 'prop-types';
import { getAssemblyVariants } from '../../config/schulatlasConfig';
import Label from '../form/Label';
import Select from '../form/Select';

export default function Variant({ assembly, onChange }) {
  const variants = getAssemblyVariants(assembly.type);
  return (
    <>
      <Label>Variants</Label>
      <Select
        id="target"
        value={assembly.variant || 'default'}
        onChange={onChange}>
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
