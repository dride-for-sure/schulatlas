import decode from 'jwt-decode';
import { array, func, object } from 'prop-types';
import { useEffect, useState } from 'react';
import styled from 'styled-components/macro';
import { v4 as uuid } from 'uuid';
import prettifySlug from '../../../../common/prettifySlug';
import { useAuth } from '../../../../contexts/AuthProvider';
import { addAttachment } from '../../../../services/api/private/attachmentApiService';
import MainButton from '../../../buttons/MainButton';
import GridEditDetails from '../../../grid/cms/GridEditDetails';
import Headline from '../../../headlines/Headline';
import Loading from '../../../loading/Loading';
import Assembly from './Assembly';

export default function EditPage({ page, updatePage, pages }) {
  const { token } = useAuth();
  const [tmpPage, setTmpPage] = useState('');
  const [assemblies, setAssemblies] = useState('');

  const submit = (event) => {
    event.preventDefault();
    if (tmpPage) { updatePage(tmpPage); }
  };

  const uploadFile = (file) =>
    addAttachment(file)
      .then((response) => response.url)
      .catch((error) => console.error(error))
      .then('');

  const updateTmpPage = () => {
    const user = decode(token);
    const updatedPage = { ...tmpPage, updated: Date.now(), userId: user.sub, assemblies };
    setTmpPage(updatedPage);
  };

  const updateAssemblies = (updatedAssembly) => {
    const updatedAssemblies = assemblies.map((assembly) =>
      (assembly.id === updatedAssembly.id ? updatedAssembly : assembly));
    setAssemblies(updatedAssemblies);
  };

  const addUuidsToAssemblies = (incomingAssemblies) =>
    incomingAssemblies.map((assembly) =>
      ({ ...assembly,
        id: uuid(),
        components: assembly.components.map((comp) =>
          (comp.components
            ? ({ ...comp,
              id: uuid(),
              components: comp.components.map((sub) => ({ ...sub, id: uuid() })),
            })
            : ({ ...comp,
              id: uuid(),
            })
          )),
      }));

  useEffect(() => {
    if (page) {
      setTmpPage(page);
      setAssemblies(addUuidsToAssemblies(page.assemblies));
    }
  }, [page]);

  useEffect(() => {
    if (assemblies) {
      updateTmpPage();
    }
  }, [assemblies]);

  if (!assemblies) {
    return <Loading />;
  }

  return (
    <Grid onSubmit={submit}>
      <Headline size="l">{prettifySlug(tmpPage.slug)}</Headline>
      <Container>
        {assemblies && assemblies.map((assembly) => (
          <Assembly
            key={assembly.id}
            assembly={assembly}
            onChange={updateAssemblies}
            onFileUpload={uploadFile}
            pages={pages} />
        ))}
      </Container>
      <MainButton>Save</MainButton>
    </Grid>
  );
}

const Grid = styled.form`
  ${GridEditDetails};
`;

const Container = styled.div`
  grid-area: fields;

  > div + div {
    margin-top: calc(var(--container-padding) * 1);
  }
`;

EditPage.propTypes = {
  page: object.isRequired,
  updatePage: func.isRequired,
  pages: array.isRequired,
};
