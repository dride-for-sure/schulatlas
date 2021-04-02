import { func, object } from 'prop-types';
import Input from '../../../form/Input';
import Label from '../../../form/Label';
import GridTwoOne from '../../../grid/cms/GridTwoOne';

export default function SchoolContact({ contact, onChange }) {
  return (
    <>
      <Label htmlFor="contact">Contact</Label>
      <GridTwoOne id="contact">
        <Input
          align="left"
          id="phone"
          type="text"
          placeholder="Please enter a phone"
          value={contact.phone}
          onChange={(event) => onChange(contact.id, { phone: event.target.value })} />
        <Input
          align="left"
          id="email"
          type="text"
          placeholder="Please enter an email"
          value={contact.email}
          onChange={(event) => onChange(contact.id, { email: event.target.value })} />
        <Input
          align="left"
          id="url"
          type="text"
          placeholder="Please enter an url"
          value={contact.url}
          onChange={(event) => onChange(contact.id, { url: event.target.value })} />
      </GridTwoOne>
    </>
  );
}

SchoolContact.propTypes = {
  contact: object.isRequired,
  onChange: func.isRequired,
};
