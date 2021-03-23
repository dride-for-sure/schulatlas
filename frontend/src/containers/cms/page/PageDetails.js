import { useParams } from 'react-router-dom';
import EditPage from '../../../components/EditPage';
import SideBar from '../../../components/SideBar';
import Center from '../../../components/structure/Center';
import CmsGrid from '../../../components/structure/CmsGrid';
import CmsWrapper from '../../../components/structure/CmsWrapper';
import MaxWidth from '../../../components/structure/MaxWidth';

export default function PageDetails() {
  const { name } = useParams();

  return (
    <CmsWrapper>
      <Center color="white">
        <MaxWidth>
          <CmsGrid>
            <SideBar />
            {name && <EditPage />}
          </CmsGrid>
        </MaxWidth>
      </Center>
    </CmsWrapper>
  );
}
