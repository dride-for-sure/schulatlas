import decode from 'jwt-decode';
import { useEffect, useRef, useState } from 'react';
import { Helmet } from 'react-helmet';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import { addIndicesToNestedData, deleteNestedData, updateNestedData } from '../../common/indexData';
import removeUsedProperties from '../../common/properties';
import { getBackendQueryString, getQueryStringForPaginate, getQueryStringForToggleSort, getSearchParams } from '../../common/searchParams';
import throttle from '../../common/throttle';
import { removeTypeless } from '../../common/types';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import PaddingContainerM from '../../components/padding/_PaddingContainerM';
import EditSchool from '../../components/parts/cms/EditSchool/EditSchool';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import GridSideBar from '../../components/structures/_GridSideBar';
import MaxWidthL from '../../components/structures/_MaxWidthL';
import { getSchoolTemplate } from '../../config/schulatlasConfig';
import { useAuth } from '../../contexts/AuthProvider';
import { addAttachment, deleteAttachmentByUrl } from '../../services/api/private/attachmentApiService';
import { listProperties } from '../../services/api/private/propertyApiService';
import { addSchool, deleteSchoolByNumber, getSchoolByNumber, getSchoolsByType, listSchools, updateSchool } from '../../services/api/private/schoolApiService';
import { listAvailableTypes, listUsedTypes } from '../../services/api/private/typeApiService';
import { searchForSchools, searchForTypes } from '../../services/api/public/searchApiService';

export default function SchoolsOverview() {
  const [usedTypes, setUsedTypes] = useState([]);
  const [availableTypes, setAvailableTypes] = useState([]);
  const [availableProperties, setAvailableProperties] = useState([]);
  const [schools, setSchools] = useState(null);
  const [school, setSchool] = useState(null);
  const [isNewSchool, setIsNewSchool] = useState(false);
  const [timer, setTimer] = useState('');
  const [searchString, setSearchString] = useState('');
  const [schoolSearchResults, setSchoolSearchResults] = useState(null);
  const [typeSearchResults, setTypeSearchResults] = useState(null);
  const history = useHistory();
  const schoolRef = useRef(school);
  const searchStringRef = useRef(searchString);
  const { type, number, searchFor } = useParams();
  const { search } = useLocation();
  const { token } = useAuth();

  const clearStates = () => {
    setSchools(null);
    setSchool(null);
    setIsNewSchool(false);
  };

  const clearSearchStates = () => {
    setTypeSearchResults(null);
    setSchoolSearchResults(null);
  };

  const handleSearchBarLeave = () => {
    clearSearchStates();
    setSearchString('');
  };

  const redirectToSearchList = () => {
    if (searchStringRef.current.length > 0) {
      history.push(`/cms/schools/search/${searchStringRef.current}`);
    }
  };

  const handleSearch = (event) => {
    const string = event.target.value;
    setSearchString(string);
    if (string < 3) {
      clearSearchStates();
      return;
    }
    const searchRequest = () => {
      if (searchStringRef.current) {
        searchForSchools(searchStringRef.current)
          .then(setSchoolSearchResults)
          .catch((error) => console.log(error));
        searchForTypes(searchStringRef.current)
          .then(setTypeSearchResults)
          .catch((error) => console.log(error));
      }
    };
    throttle(() => searchRequest(), timer, setTimer);
  };

  const getUsedTypes = () => {
    listUsedTypes()
      .then((incomingTypes) => setUsedTypes(removeTypeless(incomingTypes)))
      .catch((error) => console.log(error));
  };

  const getAvailableTypes = () => {
    listAvailableTypes()
      .then(setAvailableTypes)
      .catch((error) => console.log(error));
  };

  const getAvailableProperties = () => {
    listProperties()
      .then((incomingProperties) =>
        setAvailableProperties(
          removeUsedProperties(incomingProperties, school ? school.properties : null),
        ))
      .catch((error) => console.log(error));
  };

  const handlePagination = (whichWay) => {
    history.push(getQueryStringForPaginate(whichWay, search, schools.totalPages));
  };

  const toggleSort = (sortBy) => {
    history.push(getQueryStringForToggleSort(sortBy, search));
  };

  const addProperty = () => {
    const updatedSchool = {
      ...school,
      properties: [
        ...school.properties,
        { name: '', unit: '', value: '' },
      ] };
    setSchool(addIndicesToNestedData(updatedSchool));
  };

  const addNewSchool = () => {
    history.push('/cms/school/new-school');
  };

  const handleParamUpdates = () => {
    clearStates();
    clearSearchStates();
    setSearchString('');
    if (type) {
      getSchoolsByType(type, getBackendQueryString(search))
        .then((incomingSchool) => setSchools(addIndicesToNestedData(incomingSchool)))
        .catch((error) => console.log(error));
    } else if (searchFor) {
      searchForSchools(searchFor, getBackendQueryString(search))
        .then(setSchools)
        .catch((error) => console.log(error));
    } else if (number && number !== 'new-school') {
      getSchoolByNumber(number)
        .then((incomingSchool) => setSchool(addIndicesToNestedData(incomingSchool)))
        .catch((error) => console.log(error));
    } else if (number && number === 'new-school') {
      const newSchoolFromTemplate = getSchoolTemplate();
      setSchool(newSchoolFromTemplate);
      setIsNewSchool(true);
    } else {
      listSchools(getBackendQueryString(search))
        .then(setSchools)
        .catch((error) => console.log(error));
    }
  };

  const handleSave = () => {
    const save = () => {
      const schoolToSave = schoolRef.current;
      if (!schoolToSave.number.length) {
        return;
      }
      if (school.number !== schoolToSave.number) {
        if (!schoolToSave.newSchool) {
          deleteSchoolByNumber(school.number);
        }
        addSchool(schoolToSave)
          .then(setTimeout(getUsedTypes, 1000))
          .catch((error) => console.log(error));
      } else if (isNewSchool) {
        addSchool(schoolToSave)
          .then(setTimeout(getUsedTypes, 1000))
          .then(setIsNewSchool(false))
          .catch((error) => console.log(error));
      } else {
        updateSchool(schoolToSave, schoolToSave.number)
          .then(setTimeout(getUsedTypes, 1000))
          .catch((error) => console.log(error));
      }
    };
    throttle(() => save(), timer, setTimer);
  };

  const updateEntry = (id, entry) => {
    const user = decode(token);
    const updatedTmpSchool = {
      ...updateNestedData(school, id, entry),
      updated: Date.now(),
      userId: user.sub };
    setSchool(updatedTmpSchool);
    handleSave();
  };

  const deleteProperty = (id) => {
    const user = decode(token);
    const updatedTmpSchool = {
      ...deleteNestedData(school, id),
      updated: Date.now(),
      userId: user.sub };
    setSchool(updatedTmpSchool);
    handleSave();
  };

  const deleteSchool = (num) => {
    deleteSchoolByNumber(num);
    setSchool(null);
    setTimeout(getUsedTypes, 1000);
    history.push('/cms/schools');
  };

  const deleteFile = (url) => {
    deleteAttachmentByUrl(console.log(url));
    updateEntry(school.id, { image: '' });
  };

  const uploadFile = (event) => {
    addAttachment(event.target.files[0])
      .then((response) => updateEntry(school.id, { image: response.url }))
      .catch((error) => console.error(error));
  };

  useEffect(() => {
    handleParamUpdates();
  }, [type, number, search, searchFor]);

  useEffect(() => {
    schoolRef.current = school;
    getAvailableProperties();
  }, [school]);

  useEffect(() => {
    searchStringRef.current = searchString;
  }, [searchString]);

  useEffect(() => {
    getAvailableProperties();
    getAvailableTypes();
    getUsedTypes();
  }, []);

  return (
    <>
      <Helmet>
        <title>{school ? `SCHULATLAS CMS | Edit School: "${school.number} - ${school.name}"` : 'SCHULATLAS CMS | Schools Overview'}</title>
      </Helmet>
      <Header
        searchString={searchString}
        schoolSearchResults={schoolSearchResults}
        typeSearchResults={typeSearchResults}
        onSearchBarLeave={handleSearchBarLeave}
        onSearchBarEnter={redirectToSearchList}
        onSearch={handleSearch} />
      <Wrapper>
        <PaddingContainer>
          <MaxWidthContainer>
            {usedTypes && (
              <SideBar
                usedTypes={usedTypes}
                onAddSchool={addNewSchool} />
            )}
            {schools && (
              <SchoolList
                prefix={type || searchFor || ''}
                schools={schools}
                onPagination={handlePagination}
                toggleSort={toggleSort}
                searchParams={getSearchParams(search)} />
            )}
            {school && (
              <EditSchool
                school={school}
                availableTypes={availableTypes}
                availableProperties={availableProperties}
                onChange={updateEntry}
                onAddProperty={addProperty}
                onDeleteProperty={deleteProperty}
                onSchoolDelete={deleteSchool}
                onFileUpload={uploadFile}
                onFileDelete={deleteFile} />
            )}
          </MaxWidthContainer>
        </PaddingContainer>
      </Wrapper>
    </>
  );
}

const Wrapper = styled.main``;

const PaddingContainer = styled.div`
  ${FlexRowCenter}
  ${PaddingContainerM}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL}
  ${GridSideBar}
`;
