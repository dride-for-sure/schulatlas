import { func, object } from 'prop-types';
import styled from 'styled-components/macro';
import MainButton from '../buttons/MainButton';
import MainButtonAsSpan from '../buttons/MainButtonAsSpan';
import Input from '../form/Input';
import Label from '../form/Label';
import GridOneTwo from '../grid/cms/GridOneTwo';
import CustomImage from '../image/Image';

export default function Image({ component, onChange, onFileDelete, onFileUpload }) {
  return (
    <>
      <Label>{component.type}</Label>
      <GridOneTwo>
        <Container>
          {component.url && <CustomImage src={component.url} alt={component.description} />}
          <Input
            align="left"
            id="description"
            type="text"
            placeholder="Image description..."
            value={component.description || ''}
            onChange={onChange} />
        </Container>
        <Container>
          <Input
            align="left"
            id={`${component.id}-'fileUpload'`}
            type="file"
            accept="image/jpg, image/jpeg"
            style={{ display: 'none' }}
            onChange={onFileUpload} />
          <label htmlFor={`${component.id}-'fileUpload'`}>
            <MainButtonAsSpan variant="secondary">Upload Image</MainButtonAsSpan>
          </label>
          <MainButton
            type="button"
            id="delete"
            variant="monochrome"
            onClick={() => onFileDelete(component.id, component.url)}>
            Delete Image
          </MainButton>
        </Container>
      </GridOneTwo>
    </>
  );
}

const Container = styled.div`
  display:flex;
  flex-direction:column;

  > img {
    margin-bottom: var(--container-padding);
  }

  > label span {
    margin:0
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
