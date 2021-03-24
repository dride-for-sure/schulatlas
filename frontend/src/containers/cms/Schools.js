import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import FlexRowCenter from '../../components/flex/FlexRowCenter';
import Footer from '../../components/footer/cms/Footer';
import Grid from '../../components/grid/cms/Grid';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import SideBar from '../../components/lists/cms/sidebar/SideBarList';
import EditSchool from '../../components/parts/cms/EditSchool';
import { getSchoolByNumber, getSchoolByType, listSchools, listTypes } from '../../services/private/schoolApiService';

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

  /* TODO: LOADER */

  return (
    <>
      <Header />
      <Container>
        <Grid>
          {types && <SideBar types={types} />}
          {schools && <SchoolList schools={schools} />}
          {school && <EditSchool school={school} />}
        </Grid>
      </Container>
      <Footer />
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
