import decode from 'jwt-decode';
import { useEffect, useRef, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import debounce from '../../common/debounceChanges';
import { addIndicesToNestedData, deleteNestedData, updateNestedData } from '../../common/indexData';
import removeUsedProperties from '../../common/properties';
import { getBackendQueryString, getQueryStringForPaginate, getQueryStringForToggleSort, getSearchParams } from '../../common/searchParams';
import { removeTypeless } from '../../common/types';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import EditSchool from '../../components/parts/cms/EditSchool/EditSchool';
import SideBar from '../../components/parts/cms/SideBar';
import GridSideBar from '../../components/structures/GridSideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import { getSchoolTemplate } from '../../config/schulatlasConfig';
import { useAuth } from '../../contexts/AuthProvider';
import { addAttachment, deleteAttachmentByUrl } from '../../services/api/private/attachmentApiService';
import { listProperties } from '../../services/api/private/propertyApiService';
import { addSchool, deleteSchoolByNumber, getSchoolByNumber, getSchoolsByType, listSchools, updateSchool } from '../../services/api/private/schoolApiService';
import { listAvailableTypes, listUsedTypes } from '../../services/api/private/typeApiService';

export default function SchoolsOverview() {
  const [usedTypes, setUsedTypes] = useState('');
  const [availableTypes, setAvailableTypes] = useState('');
  const [availableProperties, setAvailableProperties] = useState('');
  const [schools, setSchools] = useState('');
  const [school, setSchool] = useState('');
  const [timer, setTimer] = useState('');
  const history = useHistory();
  const schoolRef = useRef(school);
  const { type, number } = useParams();
  const { search } = useLocation();
  const { token } = useAuth();

  const clearStates = () => {
    setSchools('');
    setSchool('');
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
        setAvailableProperties(removeUsedProperties(incomingProperties, school.properties)))
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
    if (type) {
      getSchoolsByType(type, getBackendQueryString(search))
        .then((incomingSchool) => setSchools(addIndicesToNestedData(incomingSchool)))
        .catch((error) => console.log(error));
    } else if (number && number !== 'new-school') {
      getSchoolByNumber(number)
        .then((incomingSchool) => setSchool(addIndicesToNestedData(incomingSchool)))
        .catch((error) => console.log(error));
    } else if (number && number === 'new-school') {
      const newSchoolFromTemplate = getSchoolTemplate();
      setSchool(newSchoolFromTemplate);
    } else {
      listSchools(getBackendQueryString(search))
        .then(setSchools)
        .catch((error) => console.log(error));
    }
  };

  const handleSave = () => {
    const save = () => {
      const schoolToSave = schoolRef.current;
      if (school.number !== schoolToSave.number) {
        if (!schoolToSave.newSchool) {
          deleteSchoolByNumber(school.number);
        }
        addSchool(schoolToSave)
          .then(setTimeout(getUsedTypes, 1000))
          .catch((error) => console.log(error));
      } else if (schoolToSave.newSchool) {
        const clearedSchool = {
          ...schoolToSave,
          newSchool: false,
        };
        addSchool(clearedSchool)
          .then(setTimeout(getUsedTypes, 1000))
          .then(setSchool(clearedSchool))
          .catch((error) => console.log(error));
      } else {
        updateSchool(schoolToSave, schoolToSave.number)
          .then(setTimeout(getUsedTypes, 1000))
          .catch((error) => console.log(error));
      }
    };
    debounce(() => save(), timer, setTimer);
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
    setSchool('');
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
  }, [type, number, search]);

  useEffect(() => {
    schoolRef.current = school;
  }, [school]);

  useEffect(() => {
    getAvailableProperties();
    getAvailableTypes();
    getUsedTypes();
  }, []);

  return (
    <>
      <Header />
      <Container>
        <GridSideBar>
          {usedTypes && (
          <SideBar
            usedTypes={usedTypes}
            onAddSchool={addNewSchool} />
          )}
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
              availableTypes={availableTypes}
              availableProperties={availableProperties}
              onChange={updateEntry}
              onAddProperty={addProperty}
              onDeleteProperty={deleteProperty}
              onSchoolDelete={deleteSchool}
              onFileUpload={uploadFile}
              onFileDelete={deleteFile} />
          )}
        </GridSideBar>
      </Container>
    </>
  );
}

const Container = styled.div`
  ${FlexRowCenter};
`;
