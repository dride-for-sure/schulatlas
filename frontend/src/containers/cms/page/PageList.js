import SideBar from '../../../components/SideBar';
import Center from '../../../components/structure/Center';
import CmsGrid from '../../../components/structure/CmsGrid';
import CmsWrapper from '../../../components/structure/CmsWrapper';
import MaxWidth from '../../../components/structure/MaxWidth';

export default function PageList() {
  return (
    <CmsWrapper>
      <Center color="white">
        <MaxWidth>
          <CmsGrid>
            <SideBar />
            <p>PageList</p>
          </CmsGrid>
        </MaxWidth>
      </Center>
    </CmsWrapper>
  );
}
