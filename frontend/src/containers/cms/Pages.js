import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import sortPages from '../../common/sortPages';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import EditPage from '../../components/parts/cms/EditPage/EditPage';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/FlexRowCenter';
import { getPageBySlug, listPages, updatePage } from '../../services/api/private/pageApiService';

export default function PageDetails() {
  const [page, setPage] = useState('');
  const [pages, setPages] = useState('');
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

  const handleUpdate = (pageToUpdate) => {
    updatePage(pageToUpdate, page.slug)
      .then(setPage)
      .catch((error) => console.log(error));
  };

  const addPage = () => {
    console.log('Add Page');
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
          <SideBar
            pages={pages}
            onAddPage={addPage} />
          {page
            && (
            <EditPage
              page={page}
              pages={pages}
              updatePage={handleUpdate} />
            )}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
