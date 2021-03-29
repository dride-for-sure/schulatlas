import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import EditSchool from '../../components/parts/cms/EditSchool/EditSchool';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/FlexRowCenter';
import { getSchoolByNumber, getSchoolByType, listSchools, listTypes } from '../../services/api/private/schoolApiService';

export default function SchoolsOverview() {
  const [types, setTypes] = useState('');
  const [schools, setSchools] = useState('');
  const [school, setSchool] = useState('');
  const { type, number } = useParams();

  const clearStates = () => {
    setSchools('');
    setSchool('');
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

  return (
    <>
      <Header />
      <Container>
        <GridSideBar>
          {types && <SideBar types={types} />}
          {schools && <SchoolList schools={schools} />}
          {school && <EditSchool school={school} />}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
