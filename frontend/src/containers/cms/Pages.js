import decode from 'jwt-decode';
import { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import debounce from '../../common/debounceChanges';
import { addIndicesToNestedData, updateNestedData } from '../../common/indexData';
import { escapeSlug } from '../../common/slug';
import sortPages from '../../common/sortPages';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import EditPage from '../../components/parts/cms/EditPage/EditPage';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import { getPageTemplate } from '../../config/schulatlasConfig';
import { useAuth } from '../../contexts/AuthProvider';
import { addAttachment } from '../../services/api/private/attachmentApiService';
import { addPage, deletePageBySlug, getPageBySlug, listPages, setLandingPageBySlug, updatePage } from '../../services/api/private/pageApiService';

export default function PageDetails() {
  const [page, setPage] = useState('');
  const [tmpPage, setTmpPage] = useState('');
  const [pages, setPages] = useState('');
  const [newPage, setNewPage] = useState('');
  const [timer, setTimer] = useState('');
  const tmpPageRef = useRef(tmpPage);
  const history = useHistory();
  const { slug } = useParams();
  const { token } = useAuth();

  const getPageList = () => {
    listPages()
      .then((incomingPages) => setPages(sortPages(incomingPages)))
      .catch((error) => console.log(error));
  };

  const setLandingPage = (slugToSetLandingPage) => {
    setLandingPageBySlug(slugToSetLandingPage)
      .then(getPageList)
      .catch((error) => console.log(error));
  };

  const handleParamsUpdate = () => {
    if (slug) {
      getPageBySlug(slug)
        .then(setPage)
        .catch((error) => console.log(error));
    } else {
      setPage('');
    }
  };

  const handleDelete = () => {
    deletePageBySlug(page.slug);
    setPage('');
    setTmpPage('');
    getPageList();
    history.push('/cms/pages');
  };

  const handleSave = () => {
    const save = () => {
      const pageToSave = tmpPageRef.current;
      if (pageToSave.newPage) {
        const clearedPage = {
          slug: pageToSave.slug,
          updated: pageToSave.updated,
          userId: pageToSave.userId,
          landingPage: false,
          assemblies: pageToSave.assemblies };
        addPage(clearedPage)
          .then(setPage)
          .catch((error) => console.log(error));
      } else {
        updatePage(pageToSave, page.slug)
          .then(setPage)
          .catch((error) => console.log(error));
      }
    };
    debounce(() => save(), timer, setTimer);
  };

  const updateEntry = (id, entry) => {
    const user = decode(token);
    const updatedTmpPage = {
      ...updateNestedData(tmpPage, id, entry),
      updated: Date.now(),
      userId: user.sub };
    setTmpPage(updatedTmpPage);
    handleSave();
  };

  const updateSlug = (event) => {
    const escapedSlug = escapeSlug(event.target.value);
    const user = decode(token);
    const updatedTmpPage = { ...tmpPage, updated: Date.now(), userId: user.sub, slug: escapedSlug };
    setTmpPage(updatedTmpPage);
    handleSave();
  };

  const addNewPage = (template) => {
    const pageFromTemplate = getPageTemplate(template);
    const randomIndex = Math.floor(Math.random() * 1000);
    setNewPage({ ...pageFromTemplate, slug: `${pageFromTemplate.slug}-${randomIndex}` });
  };

  const uploadFile = (id, event) =>
    addAttachment(event.target.files[0])
      .then((response) => updateEntry(id, { url: response.url }))
      .catch((error) => console.error(error));

  const deleteFile = (id) => {
    updateEntry(id, { url: '' });
  };

  useEffect(() => {
    tmpPageRef.current = tmpPage;
  }, [tmpPage]);

  useEffect(() => {
    getPageList();
    if (page) {
      setTmpPage(addIndicesToNestedData(page));
    }
  }, [page]);

  useEffect(() => {
    if (newPage) {
      setTmpPage(addIndicesToNestedData(newPage));
    }
  }, [newPage]);

  useEffect(() => {
    handleParamsUpdate();
  }, [slug]);

  useEffect(() => {
    getPageList();
  }, []);

  return (
    <>
      <Header />
      <Container>
        <GridSideBar>
          {pages && (
          <SideBar
            pages={pages}
            onAddPage={addNewPage}
            setLandingPage={setLandingPage} />
          )}
          {(tmpPage || newPage) && (
            <EditPage
              tmpPage={tmpPage}
              pages={pages}
              onChange={updateEntry}
              onUpdateSlug={updateSlug}
              onDeletePage={handleDelete}
              onUploadFile={uploadFile}
              onDeleteFile={deleteFile} />
          )}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
