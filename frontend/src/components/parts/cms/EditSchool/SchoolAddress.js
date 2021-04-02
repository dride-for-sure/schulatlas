import { func, object } from 'prop-types';
import Input from '../../../form/Input';
import Label from '../../../form/Label';
import GridTwoOne from '../../../grid/cms/GridTwoOne';

export default function SchoolAddress({ address, onChange }) {
  return (
    <>
      <Label htmlFor="address">Address</Label>
      <GridTwoOne id="address">
        <Input
          align="left"
          id="street"
          type="text"
          placeholder="Please enter a street"
          value={address.street}
          onChange={(event) => onChange(address.id, { street: event.target.value })} />
        <Input
          align="left"
          id="number"
          type="text"
          placeholder="Please enter a number"
          value={address.number}
          onChange={(event) => onChange(address.id, { number: event.target.value })} />
        <Input
          align="left"
          id="postcode"
          type="text"
          placeholder="Please enter a postcode"
          value={address.postcode}
          onChange={(event) => onChange(address.id, { postcode: event.target.value })} />
        <Input
          align="left"
          id="city"
          type="text"
          placeholder="Please enter a city"
          value={address.city}
          onChange={(event) => onChange(address.id, { city: event.target.value })} />
      </GridTwoOne>
    </>
  );
}

SchoolAddress.propTypes = {
  address: object.isRequired,
  onChange: func.isRequired,
};
