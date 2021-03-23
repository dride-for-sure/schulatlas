import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import EditSchool from '../../components/parts/cms/EditSchool';
import SideBar from '../../components/sidebar/cms/SideBar';
import Center from '../../components/structure/Center';
import CmsGrid from '../../components/structure/cms/Grid';
import CmsWrapper from '../../components/structure/cms/Wrapper';
import MaxWidth from '../../components/structure/MaxWidth';
import { getSchoolByNumber, getSchoolByType, listSchools, listTypes } from '../../services/private/schoolApiService';

export default function SchoolsOverview() {
  const [types, setTypes] = useState();
  const [schools, setSchools] = useState();
  const [school, setSchool] = useState();
  const { type, number } = useParams();

  const clearStates = () => {
    setSchools();
    setSchool();
  };

  const handleParamUpdates = () => {
    clearStates();
    if (type) {
      getSchoolByType(type)
        .then(setSchools)
        .catch((error) => console.log(error));
    } else if (number) {
      getSchoolByNumber(number)
        .then(setSchool)
        .catch((error) => console.log(error));
    } else {
      listSchools()
        .then(setSchools)
        .catch((error) => console.log(error));
    }
  };

  const getTypeList = () => {
    listTypes()
      .then(setTypes)
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    handleParamUpdates();
  }, [type, number]);

  useEffect(() => {
    getTypeList();
  }, []);

  /* TODO: LOADER */

  return (
    <CmsWrapper>
      <Center>
        <MaxWidth>
          <CmsGrid>
            {types && <SideBar types={types} />}
            {schools && <SchoolList schools={schools} />}
            {school && <EditSchool school={school} />}
          </CmsGrid>
        </MaxWidth>
      </Center>
    </CmsWrapper>
  );
}
