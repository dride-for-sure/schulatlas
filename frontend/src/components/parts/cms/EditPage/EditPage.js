import decode from 'jwt-decode';
import { array, func, object } from 'prop-types';
import { useEffect, useState } from 'react';
import { addIndizesToNestedData, updateNestedData } from '../../../../common/indexData';
import { escapeSlug, prettifySlug } from '../../../../common/slug';
import { useAuth } from '../../../../contexts/AuthProvider';
import { addAttachment } from '../../../../services/api/private/attachmentApiService';
import Slug from '../../../assemblies/Slug';
import GridForm from '../../../grid/cms/GridForm';
import Headline from '../../../headlines/Headline';
import Loading from '../../../loading/Loading';
import Assembly from './Assembly';
import SaveDelete from './SaveDelete';

export default function EditPage({ page, savePage, pages, newPage, deletePage }) {
  const { token } = useAuth();
  const [tmpPage, setTmpPage] = useState('');

  const submit = (event) => {
    event.preventDefault();
    if (tmpPage) { savePage(tmpPage); }
  };

  const updateTmpPage = (id, entry) => {
    const user = decode(token);
    const updatedTmpPage = {
      ...updateNestedData(tmpPage, id, entry),
      updated: Date.now(),
      userId: user.sub };
    setTmpPage(updatedTmpPage);
  };

  const uploadFile = (id, event) =>
    addAttachment(event.target.files[0])
      .then((response) => updateTmpPage(id, { url: response.url }))
      .catch((error) => console.error(error));

  const deleteFile = (id) => {
    updateTmpPage(id, { url: '' });
  };

  const updateSlug = (event) => {
    const slug = escapeSlug(event.target.value);
    const user = decode(token);
    const updatedPage = { ...tmpPage, updated: Date.now(), userId: user.sub, slug };
    setTmpPage(updatedPage);
  };

  useEffect(() => {
    if (page) {
      setTmpPage(addIndizesToNestedData(page));
    }
  }, [page]);

  useEffect(() => {
    if (newPage) {
      setTmpPage(addIndizesToNestedData(newPage));
    }
  }, [newPage]);

  if (!tmpPage) {
    return <Loading />;
  }

  return (
    <GridForm onSubmit={submit}>
      <Headline size="l">{prettifySlug(tmpPage.slug)}</Headline>
      <Slug
        slug={tmpPage.slug}
        onChange={updateSlug} />
      {tmpPage.assemblies.map((assembly) => (
        <Assembly
          key={assembly.id}
          assembly={assembly}
          onChange={updateTmpPage}
          onFileUpload={uploadFile}
          onFileDelete={deleteFile}
          pages={pages} />
      ))}
      <SaveDelete onDelete={() => deletePage(page.slug)} />
    </GridForm>
  );
}

EditPage.propTypes = {
  page: object.isRequired,
  savePage: func.isRequired,
  pages: array.isRequired,
  newPage: object,
  deletePage: func.isRequired,
};
