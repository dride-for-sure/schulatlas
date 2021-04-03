import decode from 'jwt-decode';
import { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import debounce from '../../common/debounceChanges';
import { addIndicesToNestedData, updateNestedData } from '../../common/indexData';
import { escapeSlug } from '../../common/slug';
import sortPages from '../../common/sortPages';
import Header from '../../components/header/cms/Header';
import EditPage from '../../components/parts/cms/EditPage/EditPage';
import SideBar from '../../components/parts/cms/SideBar';
import GridSideBar from '../../components/structures/GridSideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import { getPageTemplate } from '../../config/schulatlasConfig';
import { useAuth } from '../../contexts/AuthProvider';
import { addAttachment } from '../../services/api/private/attachmentApiService';
import { addPage, deletePageBySlug, getPageBySlug, listPages, setLandingPageBySlug, updatePage } from '../../services/api/private/pageApiService';

export default function PageDetails() {
  const [page, setPage] = useState('');
  const [pages, setPages] = useState('');
  const [timer, setTimer] = useState('');
  const [currentSlug, setCurrentSlug] = useState('');
  const pageRef = useRef(page);
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
        .then((incomingPage) => setPage(addIndicesToNestedData(incomingPage)))
        .then(setCurrentSlug(slug))
        .catch((error) => console.log(error));
    } else {
      setPage('');
    }
  };

  const handleDelete = () => {
    deletePageBySlug(slug);
    setPage('');
    getPageList();
    history.push('/cms/pages');
  };

  const handleSave = () => {
    const save = () => {
      const pageToSave = pageRef.current;
      if (pageToSave.newPage && pageToSave.slug) {
        const clearedPage = {
          slug: pageToSave.slug,
          updated: pageToSave.updated,
          userId: pageToSave.userId,
          landingPage: false,
          assemblies: pageToSave.assemblies };
        addPage(clearedPage)
          .then(setCurrentSlug(clearedPage.slug))
          .then(setPage(clearedPage))
          .then(setTimeout(getPageList, 1000))
          .catch((error) => console.log(error));
      } else if (pageToSave.slug) {
        updatePage(pageToSave, currentSlug)
          .then(setCurrentSlug(pageToSave.slug))
          .then(setTimeout(getPageList, 1000))
          .catch((error) => console.log(error));
      }
    };
    debounce(() => save(), timer, setTimer);
  };

  const updateEntry = (id, entry) => {
    const user = decode(token);
    const updatedPage = {
      ...updateNestedData(page, id, entry),
      updated: Date.now(),
      userId: user.sub };
    setPage(updatedPage);
    handleSave();
  };

  const updateSlug = (event) => {
    const escapedSlug = escapeSlug(event.target.value);
    const user = decode(token);
    const updatedPage = { ...page, updated: Date.now(), userId: user.sub, slug: escapedSlug };
    setPage(updatedPage);
    handleSave();
  };

  const addNewPage = (template) => {
    const pageFromTemplate = getPageTemplate(template);
    const randomIndex = Math.floor(Math.random() * 1000);
    setPage(addIndicesToNestedData({ ...pageFromTemplate, slug: `${pageFromTemplate.slug}-${randomIndex}` }));
  };

  const uploadFile = (id, event) =>
    addAttachment(event.target.files[0])
      .then((response) => updateEntry(id, { url: response.url }))
      .catch((error) => console.error(error));

  const deleteFile = (id) => {
    updateEntry(id, { url: '' });
  };

  useEffect(() => {
    pageRef.current = page;
  }, [page]);

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
          {page && (
            <EditPage
              page={page}
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
