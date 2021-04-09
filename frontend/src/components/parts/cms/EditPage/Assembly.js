import { array, func, object } from 'prop-types';
import { prettifySlug } from '../../../../common/slug';
import Button from '../../../assemblies/cms/Button';
import Card from '../../../assemblies/cms/Card';
import Image from '../../../assemblies/cms/Image';
import Paragraph from '../../../assemblies/cms/Paragraph';
import Title from '../../../assemblies/cms/Title';
import Variant from '../../../assemblies/cms/Variant';
import Headline from '../../../headlines/Headline';

export default function Assembly({ assembly, onChange, onFileUpload, onFileDelete, pages }) {
  const getComponentForm = (component) => {
    const { id } = component;
    switch (component.type) {
      case 'title':
      case 'title row 1':
      case 'title row 2':
      case 'subtitle':
        return (
          <Title
            key={`${id}-${component.type}`}
            component={component}
            onChange={(event) => onChange(id, { [event.target.id]: event.target.value })} />
        );
      case 'button':
        return (
          <Button
            key={id}
            component={component}
            pages={pages}
            onChange={(event) => onChange(id, { [event.target.id]: event.target.value })} />
        );
      case 'paragraph':
        return (
          <Paragraph
            key={id}
            component={component}
            onChange={(event) => onChange(id, { [event.target.id]: event.target.value })} />
        );
      case 'image':
        return (
          <Image
            key={id}
            component={component}
            onFileDelete={onFileDelete}
            onFileUpload={(event) => onFileUpload(id, event)}
            onChange={(event) => onChange(id, { [event.target.id]: event.target.value })} />
        );
      case 'card':
        return (
          <Card
            key={id}
            component={component}
            pages={pages}
            onChange={(event) => onChange(id, { [event.target.id]: event.target.value })} />
        );
      default:
        return '';
    }
  };

  return (
    <>
      <Headline>{prettifySlug(assembly.type)}</Headline>
      {assembly.variant && (
        <Variant
          assembly={assembly}
          onChange={onChange} />
      )}
      {assembly.components.map((component) => getComponentForm(component))}
    </>

  );
}

Assembly.propTypes = {
  assembly: object.isRequired,
  onChange: func.isRequired,
  onFileUpload: func.isRequired,
  onFileDelete: func.isRequired,
  pages: array.isRequired,
};
