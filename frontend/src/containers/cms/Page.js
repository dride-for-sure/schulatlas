import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import EditPage from '../../components/parts/cms/EditPage';
import SideBar from '../../components/sidebar/cms/SideBar';
import Center from '../../components/structure/Center';
import CmsGrid from '../../components/structure/cms/Grid';
import CmsWrapper from '../../components/structure/cms/Wrapper';
import MaxWidth from '../../components/structure/MaxWidth';
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
    getPageList();
  }, []);

  useEffect(() => {
    handleParamsUpdate();
  }, [name]);

  /* TODO: LOADER */

  return (
    <CmsWrapper>
      <Center>
        <MaxWidth>
          <CmsGrid>
            <SideBar pages={pages} />
            {page && <EditPage page={page} />}
          </CmsGrid>
        </MaxWidth>
      </Center>
    </CmsWrapper>
  );
}
