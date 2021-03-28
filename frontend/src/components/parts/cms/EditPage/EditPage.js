import decode from 'jwt-decode';
import { array, func, object } from 'prop-types';
import { useEffect, useState } from 'react';
import styled from 'styled-components/macro';
import { v4 as uuid } from 'uuid';
import { escapeSlug, prettifySlug } from '../../../../common/slugHelper';
import { useAuth } from '../../../../contexts/AuthProvider';
import { addAttachment } from '../../../../services/api/private/attachmentApiService';
import Slug from '../../../assemblies/Slug';
import MainButton from '../../../buttons/MainButton';
import GridEditDetails from '../../../grid/cms/GridEditDetails';
import Headline from '../../../headlines/Headline';
import Loading from '../../../loading/Loading';
import Assembly from './Assembly';

export default function EditPage({ page, savePage, pages, newPage, deletePage }) {
  const { token } = useAuth();
  const [tmpPage, setTmpPage] = useState('');
  const [assemblies, setAssemblies] = useState('');

  const submit = (event) => {
    event.preventDefault();
    if (tmpPage) { savePage(tmpPage); }
  };

  const uploadFile = (file) =>
    addAttachment(file)
      .then((response) => response.url)
      .catch((error) => console.error(error));

  const updateTmpPage = () => {
    const user = decode(token);
    const updatedPage = { ...tmpPage, updated: Date.now(), userId: user.sub, assemblies };
    setTmpPage(updatedPage);
  };

  const updateSlug = (event) => {
    const slug = escapeSlug(event.target.value);
    const user = decode(token);
    const updatedPage = { ...tmpPage, updated: Date.now(), userId: user.sub, slug };
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

  useEffect(() => {
    if (newPage) {
      setTmpPage(newPage);
      setAssemblies(addUuidsToAssemblies(newPage.assemblies));
    }
  }, [newPage]);

  if (!assemblies) {
    return <Loading />;
  }

  return (
    <Grid onSubmit={submit}>
      <Headline size="l">{prettifySlug(tmpPage.slug)}</Headline>
      <Form>
        <InnerGrid>
          <Slug
            slug={tmpPage.slug}
            onChange={updateSlug} />
        </InnerGrid>
        {assemblies && assemblies.map((assembly) => (
          <Assembly
            key={assembly.id}
            assembly={assembly}
            onChange={updateAssemblies}
            onFileUpload={uploadFile}
            pages={pages} />
        ))}
      </Form>
      <Container>
        <MainButton type="button" variant="monochrome" onClick={() => deletePage(page.slug)}>Delete</MainButton>
        <MainButton>Save</MainButton>
      </Container>
    </Grid>
  );
}

const InnerGrid = styled.div`
  ${GridEditDetails};
  grid-gap: var(--container-padding);
`;

const Grid = styled.form`
  ${GridEditDetails};
  grid-template-areas:
    ". headline"
    "fields fields"
    ". submit";
    
`;

const Container = styled.div`
  grid-area: submit;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;

  button + button {
    margin-left: var(--container-padding);
  }
`;

const Form = styled.div`
  grid-area: fields;

  > div + div {
    margin-top: calc(var(--container-padding) * 1);
  }
`;

EditPage.propTypes = {
  page: object.isRequired,
  savePage: func.isRequired,
  pages: array.isRequired,
  newPage: object,
  deletePage: func.isRequired,
};
