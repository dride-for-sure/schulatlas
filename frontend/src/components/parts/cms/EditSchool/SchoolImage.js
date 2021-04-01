import { func, string } from 'prop-types';
import styled from 'styled-components/macro';
import { v4 as uuid } from 'uuid';
import BrandButton from '../../../buttons/BrandButton';
import BrandButtonAsSpan from '../../../buttons/BrandButtonAsSpan';
import Input from '../../../form/Input';
import Label from '../../../form/Label';
import GridTwoOne from '../../../grid/cms/GridTwoOne';
import CustomImage from '../../../image/Image';

export default function SchoolImage({ imageUrl, onFileUpload, onFileDelete }) {
  const uniqueFieldId = uuid();
  return (
    <>
      <Label>School Image</Label>
      <GridTwoOne>
        <Left>
          {imageUrl && <CustomImage src={imageUrl} alt="" />}
        </Left>
        <Right>
          <Input
            align="left"
            id={`${uniqueFieldId}-'fileUpload'`}
            type="file"
            accept="image/jpg, image/jpeg"
            style={{ display: 'none' }}
            onChange={onFileUpload} />
          <BrandButtonAsSpan
            htmlFor={`${uniqueFieldId}-'fileUpload'`}
            variant="secondary">
            Upload Image
          </BrandButtonAsSpan>
          <BrandButton
            type="button"
            id="delete"
            variant="monochrome"
            onClick={() => onFileDelete(imageUrl)}>
            Delete Image
          </BrandButton>
        </Right>
      </GridTwoOne>
    </>
  );
}

const Left = styled.div`
`;

const Right = styled.div`
   > label span {
    margin: 0;
  }

  > button {
    margin: var(--container-padding) 0;
  }
`;

SchoolImage.propTypes = {
  imageUrl: string.isRequired,
  onFileUpload: func.isRequired,
  onFileDelete: func.isRequired,
};
