import { func, object } from 'prop-types';
import styled from 'styled-components/macro';
import BrandButton from '../buttons/BrandButton';
import BrandButtonAsSpan from '../buttons/BrandButtonAsSpan';
import Input from '../form/Input';
import Label from '../form/Label';
import GridTwoOne from '../grid/cms/GridTwoOne';
import CustomImage from '../image/Image';

export default function Image({ component, onChange, onFileDelete, onFileUpload }) {
  return (
    <>
      <Label>{component.type}</Label>
      <GridTwoOne>
        <ColumnLeft>
          {component.url && <CustomImage src={component.url} alt="" />}
          <Input
            align="left"
            id="description"
            type="text"
            placeholder="Image description..."
            value={component.description || ''}
            onChange={onChange} />
        </ColumnLeft>
        <ColumnRight>
          <Input
            align="left"
            id={`${component.id}-'fileUpload'`}
            type="file"
            accept="image/jpg, image/jpeg"
            style={{ display: 'none' }}
            onChange={onFileUpload} />
          <BrandButtonAsSpan
            htmlFor={`${component.id}-'fileUpload'`}
            variant="secondary">
            Upload Image
          </BrandButtonAsSpan>
          <BrandButton
            type="button"
            id="delete"
            variant="monochrome"
            onClick={() => onFileDelete(component.id, component.url)}>
            Delete Image
          </BrandButton>
        </ColumnRight>
      </GridTwoOne>
    </>
  );
}

const ColumnLeft = styled.div`
  > img {
    margin-bottom: var(--container-padding);
  }
`;

const ColumnRight = styled.div`
   > label span {
    margin: 0;
  }

  > button {
    margin: var(--container-padding) 0;
  }
`;

Image.propTypes = {
  component: object.isRequired,
  onChange: func.isRequired,
  onFileDelete: func.isRequired,
  onFileUpload: func.isRequired,
};
