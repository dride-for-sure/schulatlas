import { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import sortPages from '../../common/sortPages';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import EditPage from '../../components/parts/cms/EditPage/EditPage';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import { getPageTemplate } from '../../config/schulatlasConfig';
import { addPage, deletePageBySlug, getPageBySlug, listPages, setLandingPageBySlug, updatePage } from '../../services/api/private/pageApiService';

export default function PageDetails() {
  const [page, setPage] = useState('');
  const [pages, setPages] = useState('');
  const [newPage, setNewPage] = useState('');
  const history = useHistory();
  const { slug } = useParams();

  const getPageList = () => {
    listPages()
      .then((incomingPages) => setPages(sortPages(incomingPages)))
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

  const setLandingPage = (slugToSetLandingPage) => {
    setLandingPageBySlug(slugToSetLandingPage)
      .then(getPageList)
      .catch((error) => console.log(error));
  };

  const handleDelete = (slugToDelete) => {
    deletePageBySlug(slugToDelete);
    setPage('');
    getPageList();
    history.push('/cms/pages');
  };

  const handleSave = (pageToSave) => {
    if (pageToSave.newPage === true) {
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

  const addNewPage = (template) => {
    const pageFromTemplate = getPageTemplate(template);
    setNewPage(pageFromTemplate);
  };

  useEffect(() => {
    getPageList();
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
          {(page || newPage) && (
            <EditPage
              page={page}
              pages={pages}
              newPage={newPage}
              savePage={handleSave}
              deletePage={handleDelete} />
          )}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
