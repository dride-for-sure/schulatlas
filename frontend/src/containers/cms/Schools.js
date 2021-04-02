import decode from 'jwt-decode';
import { useEffect, useRef, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import debounce from '../../common/debounceChanges';
import { addIndicesToNestedData, deleteNestedData, updateNestedData } from '../../common/indexData';
import removeUsedProperties from '../../common/properties';
import { getBackendQueryString, getQueryStringForPaginate, getQueryStringForToggleSort, getSearchParams } from '../../common/searchParams';
import { removeTypeless } from '../../common/types';
import GridSideBar from '../../components/grid/cms/GridSideBar';
import Header from '../../components/header/cms/Header';
import SchoolList from '../../components/lists/cms/school/SchoolList';
import EditSchool from '../../components/parts/cms/EditSchool/EditSchool';
import SideBar from '../../components/parts/cms/SideBar';
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
  const [newSchool, setNewSchool] = useState('');
  const [tmpSchool, setTmpSchool] = useState();
  const [timer, setTimer] = useState('');
  const history = useHistory();
  const tmpSchoolRef = useRef(tmpSchool);
  const { type, number } = useParams();
  const { search } = useLocation();
  const { token } = useAuth();

  const clearStates = () => {
    setSchools('');
    setSchool('');
    setNewSchool('');
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
        setAvailableProperties(removeUsedProperties(incomingProperties, tmpSchool.properties)))
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
      ...tmpSchool,
      properties: [
        ...tmpSchool.properties,
        { name: '', unit: '', value: '' },
      ] };
    setTmpSchool(addIndicesToNestedData(updatedSchool));
  };

  const addNewSchool = () => {
    history.push('/cms/school/new-school');
    const newSchoolFromTemplate = getSchoolTemplate();
    setNewSchool(newSchoolFromTemplate);
  };

  const handleParamUpdates = () => {
    clearStates();
    if (type) {
      getSchoolsByType(type, getBackendQueryString(search))
        .then(setSchools)
        .catch((error) => console.log(error));
    } else if (number && number !== 'new-school') {
      getSchoolByNumber(number)
        .then(setSchool)
        .catch((error) => console.log(error));
    } else if (number && number === 'new-school') {
      addNewSchool();
    } else {
      listSchools(getBackendQueryString(search))
        .then(setSchools)
        .catch((error) => console.log(error));
    }
  };

  const handleSave = () => {
    const save = () => {
      const schoolToSave = tmpSchoolRef.current;
      if (school.number !== schoolToSave.number) {
        if (!schoolToSave.newSchool) {
          deleteSchoolByNumber(school.number);
        }
        addSchool(schoolToSave)
          .then(setSchool)
          .catch((error) => console.log(error));
      } else if (schoolToSave.newSchool) {
        addSchool(schoolToSave)
          .then(setSchool)
          .catch((error) => console.log(error));
      } else {
        updateSchool(schoolToSave, schoolToSave.number)
          .then(setSchool)
          .catch((error) => console.log(error));
      }
    };
    debounce(() => save(), timer, setTimer);
  };

  const updateEntry = (id, entry) => {
    const user = decode(token);
    const updatedTmpSchool = {
      ...updateNestedData(tmpSchool, id, entry),
      updated: Date.now(),
      userId: user.sub };
    setTmpSchool(updatedTmpSchool);
    handleSave();
  };

  const deleteProperty = (id) => {
    const user = decode(token);
    const updatedTmpSchool = {
      ...deleteNestedData(tmpSchool, id),
      updated: Date.now(),
      userId: user.sub };
    setTmpSchool(updatedTmpSchool);
    handleSave();
  };

  const deleteSchool = (num) => {
    deleteSchoolByNumber(num);
    setSchool('');
    setTmpSchool('');
    history.push('/cms/schools');
  };

  const deleteFile = (url) => {
    deleteAttachmentByUrl(console.log(url));
    updateEntry(tmpSchool.id, { image: '' });
  };

  const uploadFile = (event) => {
    addAttachment(event.target.files[0])
      .then((response) => updateEntry(tmpSchool.id, { image: response.url }))
      .catch((error) => console.error(error));
  };

  useEffect(() => {
    tmpSchoolRef.current = tmpSchool;
    if (tmpSchool) {
      getAvailableProperties();
    } else {
      getUsedTypes();
    }
  }, [tmpSchool]);

  useEffect(() => {
    handleParamUpdates();
  }, [type, number, search]);

  useEffect(() => {
    if (newSchool) {
      setTmpSchool(addIndicesToNestedData(newSchool));
    }
  }, [newSchool]);

  useEffect(() => {
    if (school) {
      setTmpSchool(addIndicesToNestedData(school));
      getUsedTypes();
    }
  }, [school]);

  useEffect(() => {
    getAvailableTypes();
  }, [usedTypes]);

  useEffect(() => {
    getAvailableTypes();
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
          {(school || newSchool) && (
            <EditSchool
              tmpSchool={tmpSchool}
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
