import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import FlexRowCenter from '../../components/flex/FlexRowCenter';
import Footer from '../../components/footer/cms/Footer';
import Grid from '../../components/grid/cms/Grid';
import Header from '../../components/header/cms/Header';
import SideBar from '../../components/lists/cms/sidebar/SideBarList';
import EditPage from '../../components/parts/cms/EditPage';
import { getPageByName, listPages } from '../../services/private/pageApiService';

export default function PageDetails() {
  const [page, setPage] = useState();
  const [pages, setPages] = useState();
  const { name } = useParams();

  const getPageList = () => {
    listPages()
      .then(setPages)
      .catch((error) => console.log(error));
  };

  const handleParamsUpdate = () => {
    if (name) {
      getPageByName(name)
        .then(setPage)
        .catch((error) => console.log(error));
    } else {
      setPage();
    }
  };

  useEffect(() => {
    handleParamsUpdate();
  }, [name]);

  useEffect(() => {
    getPageList();
  }, []);

  /* TODO: LOADER */

  return (
    <>
      <Header />
      <Container>
        <Grid>
          <SideBar pages={pages} />
          {page && <EditPage page={page} />}
        </Grid>
      </Container>
      <Footer />
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
