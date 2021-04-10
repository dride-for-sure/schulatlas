import { useEffect, useRef, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import throttle from '../../common/throttle';
import Atlas from '../../components/assemblies/schulatlas/atlas/atlas';
import CTA from '../../components/assemblies/schulatlas/cta/CTA';
import Footer from '../../components/assemblies/schulatlas/footer/Footer';
import ErrorNotAvailable from '../../components/error/ErrorNotAvailable';
import HeaderMaps from '../../components/header/schulatlas/HeaderMaps';
import Loading from '../../components/loading/Loading';
import { getSchoolByNumber } from '../../services/api/private/schoolApiService';
import { getLandingPage } from '../../services/api/public/pageApiService';
import { searchForSchools, searchForTypes } from '../../services/api/public/searchApiService';

export default function Maps() {
  const [maps, setMaps] = useState(null);
  const [school, setSchool] = useState(null);
  const [schools, setSchools] = useState(null);
  const [searchString, setSearchString] = useState(null);
  const [schoolSearchResults, setSchoolSearchResults] = useState(null);
  const [typeSearchResults, setTypeSearchResults] = useState(null);
  const [timer, setTimer] = useState(null);
  const [error, setError] = useState(false);
  const searchStringRef = useRef(searchString);
  const { search } = useLocation();
  const { number, type, searchFor } = useParams();
  const history = useHistory();

  const getCTA = () => maps.assemblies.find((assembly) => assembly.type === 'cta');

  const getSchool = (schoolNumber) => {
    getSchoolByNumber(schoolNumber)
      .then(setSchool)
      .catch((e) => console.error(e));
  };

  const getSchools = (section) => {
    console.log(section);
    console.log(setSchools);
  };

  const clearSchool = () => {
    setSchool(null);
  };

  const handleSearchBarLeave = () => {
    setTypeSearchResults(null);
    setSchoolSearchResults(null);
    setSearchString('');
  };

  const redirectToSearchList = () => {
    if (searchStringRef.current.length > 0) {
      history.push(`/maps/search/${searchStringRef.current}${search}`);
    }
  };

  const handleSearch = (event) => {
    const string = event.target.value;
    setSearchString(string);
    if (string < 3) {
      setTypeSearchResults(null);
      setSchoolSearchResults(null);
      return;
    }
    const searchRequest = () => {
      if (searchStringRef.current) {
        searchForSchools(searchStringRef.current)
          .then(setSchoolSearchResults)
          .catch((e) => console.error(e));
        searchForTypes(searchStringRef.current)
          .then(setTypeSearchResults)
          .catch((e) => console.error(e));
      }
    };
    throttle(() => searchRequest(), timer, setTimer);
  };

  const handleParamUpdates = () => {
    setSchool(null);
    setSchools(null);
    setTypeSearchResults(null);
    setSchoolSearchResults(null);
    setSearchString('');
    if (number) {
      console.log(`Get school by number: ${number}`);
      if (search) {
        console.log(`Preserve view: ${search}`);
      }
    } else if (type) {
      console.log(`Show schools for type: ${type}`);
      if (search) {
        console.log(`Preserve view: ${search}`);
      }
    } else if (searchFor) {
      console.log(`Search for: ${searchFor}`);
      if (search) {
        console.log(`Within this area: ${search}`);
      }
    } else {
      console.log('Load default map based on user position');
    }
  };

  useEffect(() => {
    handleParamUpdates();
  }, [search, number]);

  useEffect(() => {
    searchStringRef.current = searchString;
  }, [searchString]);

  // TODO: replace with getMapsPage
  useEffect(() => {
    getLandingPage()
      .then(setMaps)
      .catch(() => setError(true));
  }, []);

  if (!maps && !error) {
    return <Loading />;
  }

  if (error) {
    return <ErrorNotAvailable error={error} />;
  }

  return (
    <>
      <HeaderMaps
        searchString={searchString}
        schoolSearchResults={schoolSearchResults}
        typeSearchResults={typeSearchResults}
        onSearchBarLeave={handleSearchBarLeave}
        onSearchBarEnter={redirectToSearchList}
        onSearch={handleSearch}
        searchQueries={search}
      />
      <Atlas
        school={school}
        clearSchool={clearSchool}
        getSchool={getSchool}
        schools={schools}
        getSchools={getSchools} />
      <CTA assembly={getCTA()} />
      <Footer />
    </>
  );
}
