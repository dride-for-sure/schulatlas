import { useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import { getBackendQueryString, getQueryStringForPaginate, getQueryStringForToggleSort, getSearchParams } from '../../common/searchParams';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import EditSchool from '../../components/parts/cms/EditSchool/EditSchool';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import { getSchoolByNumber, getSchoolsByType, listSchools, listTypes, updateSchool } from '../../services/api/private/schoolApiService';

export default function SchoolsOverview() {
  const [types, setTypes] = useState('');
  const [schools, setSchools] = useState('');
  const [school, setSchool] = useState('');
  const { type, number } = useParams();
  const { search } = useLocation();
  const history = useHistory();

  const clearStates = () => {
    setSchools('');
    setSchool('');
  };

  const handleParamUpdates = () => {
    clearStates();
    if (type) {
      getSchoolsByType(type)
        .then(setSchools)
        .catch((error) => console.log(error));
    } else if (number) {
      getSchoolByNumber(number)
        .then(setSchool)
        .catch((error) => console.log(error));
    } else {
      listSchools(getBackendQueryString(search))
        .then(setSchools)
        .catch((error) => console.log(error));
    }
  };

  const getTypeList = () => {
    listTypes()
      .then(setTypes)
      .catch((error) => console.log(error));
  };

  const handlePagination = (whichWay) => {
    history.push(getQueryStringForPaginate(whichWay, search, schools.totalPages));
  };

  const toggleSort = (sortBy) => {
    history.push(getQueryStringForToggleSort(sortBy, search));
  };

  const handleSave = (schoolToSave) => {
    if (schoolToSave.new) {
      // Add new school ... next PR
    } else {
      updateSchool(schoolToSave, schoolToSave.number)
        .then(setSchool)
        .catch((error) => console.log(error));
    }
  };

  useEffect(() => {
    handleParamUpdates();
  }, [type, number, search]);

  useEffect(() => {
    getTypeList();
  }, []);

  return (
    <>
      <Header />
      <Container>
        <GridSideBar>
          {types && <SideBar types={types} />}
          {schools && (
          <SchoolList
            type={type}
            schools={schools}
            onPagination={handlePagination}
            toggleSort={toggleSort}
            searchParams={getSearchParams(search)}
             />
          )}
          {school && (
          <EditSchool
            school={school}
            saveSchool={handleSave} />
          )}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
