import { array, func, object } from 'prop-types';
import styled from 'styled-components/macro';
import { prettifySlug } from '../../../../common/slug';
import Button from '../../../assemblies/Button';
import Card from '../../../assemblies/Card';
import Image from '../../../assemblies/Image';
import Paragraph from '../../../assemblies/Paragraph';
import Title from '../../../assemblies/Title';
import Variant from '../../../assemblies/Variant';
import GridEditDetails from '../../../grid/cms/_GridEditDetails';
import Headline from '../../../headlines/Headline';

export default function Assembly({ assembly, onChange, onFileUpload, pages }) {
  const updateAssembly = (idToUpdate, key, value) => {
    const updatedComponents = assembly.components.map((component) => {
      if (component.id === idToUpdate) {
        return ({ ...component, [key]: value });
      }
      return component;
    });
    onChange({ ...assembly, components: updatedComponents });
  };

  const updateVariant = (event) => {
    const variant = event.target.value;
    onChange({ ...assembly, variant });
  };

  const handleFileUpload = (event, id) => {
    onFileUpload(event.target.files[0])
      .then((url) => updateAssembly(id, 'url', url));
  };

  const handleFileDelete = (id) => {
    updateAssembly(id, 'url', '');
  };

  const getComponentForm = (component) => {
    switch (component.type) {
      case 'title':
      case 'subtitle':
        return (
          <Title
            key={component.id}
            component={component}
            onChange={(event) =>
              updateAssembly(component.id, event.target.id, event.target.value)} />
        );
      case 'button':
        return (
          <Button
            key={component.id}
            component={component}
            onChange={(event) =>
              updateAssembly(component.id, event.target.id, event.target.value)}
            pages={pages} />
        );
      case 'paragraph':
        return (
          <Paragraph
            key={component.id}
            component={component}
            onChange={(event) =>
              updateAssembly(component.id, event.target.id, event.target.value)} />
        );
      case 'image':
        return (
          <Image
            key={component.id}
            component={component}
            onChange={(event) =>
              updateAssembly(component.id, event.target.id, event.target.value)}
            onFileUpload={(event) => handleFileUpload(event, component.id)}
            onFileDelete={handleFileDelete} />
        );
      case 'card':
        return (
          <Card
            key={component.id}
            component={component}
            pages={pages}
            onChange={(event) =>
              updateAssembly(component.id, event.target.id, event.target.value)} />
        );
      default:
        return '';
    }
  };

  return (
    <OuterGrid>
      <Headline>{prettifySlug(assembly.type)}</Headline>
      <InnerGrid>
        {assembly.variant && (
          <Variant
            assembly={assembly}
            onChange={updateVariant} />
        )}
        {assembly.components.map((component) => getComponentForm(component))}
      </InnerGrid>
    </OuterGrid>
  );
}

const OuterGrid = styled.div`
  ${GridEditDetails};
  > div {
    grid-area: fields;
  }

`;

const InnerGrid = styled.div`
  ${GridEditDetails};
  grid-gap: var(--container-padding);
`;

Assembly.propTypes = {
  assembly: object.isRequired,
  onChange: func.isRequired,
  onFileUpload: func.isRequired,
  pages: array.isRequired,
};
