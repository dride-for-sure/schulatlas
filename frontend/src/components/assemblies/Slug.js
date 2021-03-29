import { func, string } from 'prop-types';
import styled from 'styled-components';
import Input from '../form/Input';
import Label from '../form/Label';
import GridEditDetails from '../grid/cms/GridEditDetails';

export default function Slug({ slug, onChange }) {
  return (
    <InnerGrid>
      <Label>Slug</Label>
      <Input
        align="left"
        id="slug"
        type="text"
        placeholder="Enter page slug"
        value={slug || ''}
        onChange={onChange} />
    </InnerGrid>
  );
}

const InnerGrid = styled.div`
  ${GridEditDetails};
  grid-gap: var(--container-padding);
`;

Slug.propTypes = {
  slug: string.isRequired,
  onChange: func.isRequired,
};
