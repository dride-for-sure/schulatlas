import { useEffect, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import getSearchParams from '../../common/searchParams';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import EditSchool from '../../components/parts/cms/EditSchool/EditSchool';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import { getSchoolByNumber, getSchoolsByType, listSchools, listTypes } from '../../services/api/private/schoolApiService';

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
      const params = getSearchParams(search);
      listSchools(params.page, params.size, params.sort, params.direction)
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
    const params = getSearchParams(search);
    let targetPage = 0;
    if (whichWay === 'last') {
      targetPage = (params.page === null || params.page <= 0)
        ? 0
        : params.page - 1;
    } else {
      targetPage = (params.page === null || params.page >= schools.totalPages - 1)
        ? schools.totalPages - 1
        : params.page + 1;
    }
    history.push(`?page=${targetPage}&size=${params.size}&sort=${params.sort}&direction=${params.direction}`);
  };

  const toggleSort = (sortType) => {
    const params = getSearchParams(search);
    const direction = params.direction === 'asc' ? 'desc' : 'asc';
    history.push(`?page=${params.page}&size=${params.size}&sort=${sortType}&direction=${direction}`);
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
             />
          )}
          {school && <EditSchool school={school} />}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
