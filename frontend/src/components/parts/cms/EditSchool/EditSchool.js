import { func, object } from 'prop-types';
import { useEffect, useState } from 'react';
import { prettifySlug } from '../../../../common/slug';
import { addAttachment } from '../../../../services/api/private/attachmentApiService';
import GridForm from '../../../grid/cms/GridForm';
import Headline from '../../../headlines/Headline';
import Loading from '../../../loading/Loading';
import SaveDelete from '../EditPage/SaveDelete';
import SchoolImage from './SchoolImage';
import SchoolProperty from './SchoolProperty';
import SchoolSummary from './SchoolSummary';

export default function EditSchool({ school, saveSchool, deleteSchool }) {
  const [tmpSchool, setTmpSchool] = useState();

  const submit = (event) => {
    event.preventDefault();
    if (tmpSchool) { saveSchool(tmpSchool); }
  };

  const updateProperty = (name, value) => {
    if (tmpSchool[name]) {
      setTmpSchool({ ...tmpSchool, [name]: value });
    } else {
      const updatedProperties = tmpSchool.properties.map((property) =>
        (property.name === name ? { ...property, value } : property));
      setTmpSchool({ ...tmpSchool, properties: updatedProperties });
    }
  };

  const handlePropertyChange = (event) => {
    updateProperty(event.target.id, event.target.value);
  };

  const handleFileUpload = (event) => {
    addAttachment(event.target.files[0])
      .then((response) => updateProperty('image', response.url))
      .catch((error) => console.error(error));
  };

  const handleFileDelete = (id) => {
    updateProperty(id, 'url', '');
  };

  useEffect(() => {
    if (school) {
      setTmpSchool(school);
    }
  }, [school]);

  if (!tmpSchool) {
    return <Loading />;
  }

  return (
    <GridForm onSubmit={submit}>
      <Headline size="l">{prettifySlug(tmpSchool.name)}</Headline>
      <SchoolSummary school={school} />
      <SchoolImage
        imageUrl={tmpSchool.image}
        onFileUpload={handleFileUpload}
        onFileDelete={handleFileDelete} />
      {tmpSchool.properties.map((property) => (
        <SchoolProperty
          key={property.name}
          property={property}
          onChange={handlePropertyChange} />
      ))}
      <SaveDelete onDelete={() => deleteSchool(tmpSchool.number)} />
    </GridForm>
  );
}

EditSchool.propTypes = {
  school: object.isRequired,
  saveSchool: func.isRequired,
  deleteSchool: func.isRequired,
};
