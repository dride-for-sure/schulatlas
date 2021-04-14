import { useEffect, useRef, useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import throttle from '../../common/throttle';
import CTA from '../../components/assemblies/schulatlas/cta/CTA';
import Footer from '../../components/assemblies/schulatlas/footer/Footer';
import Map from '../../components/assemblies/schulatlas/map/Map';
import ErrorNotAvailable from '../../components/error/ErrorNotAvailable';
import HeaderMaps from '../../components/header/schulatlas/HeaderMaps';
import Loading from '../../components/loading/Loading';
import { searchMapByBounds, searchMapByBoundsAndType, searchMapByString } from '../../services/api/public/mapsApiService';
import { getLandingPage } from '../../services/api/public/pageApiService';
import { getSchoolByNumber } from '../../services/api/public/schoolApiService';
import { searchForSchools, searchForTypes } from '../../services/api/public/searchApiService';

export default function Maps() {
  const [maps, setMaps] = useState(null);
  const [hasMoved, setHasMoved] = useState(false);
  const [schools, setSchools] = useState([]);
  const [selectedSchools, setSelectedSchools] = useState([]);
  const [schoolDetails, setSchoolDetails] = useState(null);
  const [searchString, setSearchString] = useState(null);
  const [schoolSearchResults, setSchoolSearchResults] = useState(null);
  const [typeSearchResults, setTypeSearchResults] = useState(null);
  const [timer, setTimer] = useState(null);
  const [error, setError] = useState(false);
  const [bounds, setBounds] = useState(null);
  const searchStringRef = useRef(searchString);
  const { search } = useLocation();
  const { number, type, searchFor } = useParams();
  const history = useHistory();

  const getCTA = () => maps.assemblies.find((assembly) => assembly.type === 'cta');

  const handleSearchBarLeave = () => {
    setTypeSearchResults(null);
    setSchoolSearchResults(null);
  };

  const handleSearchBarClear = () => {
    handleSearchBarLeave();
    setSearchString('');
    setSchoolDetails(null);
    history.push('/maps');
  };

  const redirectToSearchList = () => {
    if (searchStringRef.current.length > 0) {
      history.push(`/maps/search/${searchStringRef.current}${search}`);
    }
  };

  const handleMarkerClick = (event) => {
    setSearchString(event.target.options.name);
    history.push(`/maps/school/${event.target.options.id}`);
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
      history.push('/maps');
      setSchoolDetails(null);
      setSelectedSchools([]);
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

  const handleSingleMapSelection = (selectedSchool) => {
    setSearchString(selectedSchool.name);
    setSchoolDetails(selectedSchool);
    setSelectedSchools([selectedSchool]);
  };

  const handleParamUpdates = () => {
    setTypeSearchResults(null);
    setSchoolSearchResults(null);
    setSchoolDetails(null);
    setSelectedSchools([]);
    setHasMoved(false);
    if (number) {
      getSchoolByNumber(number)
        .then((data) => handleSingleMapSelection(data))
        .catch(() => setSelectedSchools([]));
    } else if (type) {
      searchMapByBoundsAndType(type, bounds)
        .then((data) => setSelectedSchools(data))
        .catch(() => setSelectedSchools([]));
    } else if (searchFor) {
      searchMapByString(searchFor, bounds)
        .then((data) => setSelectedSchools(data))
        .catch(() => setSelectedSchools([]));
    }
  };

  const handleBoundsChange = () => {
    searchMapByBounds(bounds)
      .then((data) => setSchools(data))
      .catch(() => setSchools([]));
  };

  useEffect(() => {
    setError(null);
    handleParamUpdates();
  }, [search, number, type, searchFor]);

  useEffect(() => {
    if (bounds !== null) {
      handleBoundsChange();
    }
  }, [bounds]);

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
        onSearchBarClear={handleSearchBarClear}
        onSearch={handleSearch}
        schoolDetails={schoolDetails}
        searchQueries={search}
      />
      <Map
        schools={schools}
        selectedSchools={selectedSchools}
        onClick={handleMarkerClick}
        onBoundsChange={setBounds}
        hasMoved={hasMoved}
        setHasMoved={setHasMoved} />
      <CTA assembly={getCTA()} />
      <Footer />
    </>
  );
}
