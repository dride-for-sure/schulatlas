import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { array, bool, func } from 'prop-types';
import { useEffect, useRef } from 'react';
import styled from 'styled-components/macro';
import { getIconByType, getSelectionIconByType } from '../../../../common/mapsIcons';
import BrandBar from '../../../brandBar/_BrandBar';

export default function Map({ schools, selectedSchools, onClick,
  onBoundsChange, hasMoved, setHasMoved }) {
  const mapRef = useRef(null);
  const layerRef = useRef(null);
  const selectionLayerRef = useRef(null);

  const removeDuplicates = (schoolsToClean, selectedSchoolsToSubstract) =>
    schoolsToClean.filter((data) =>
      !selectedSchoolsToSubstract.some((dataToSubstract) =>
        data.number === dataToSubstract.number));

  const addDataToMap = (dataToAdd, isSelection) => {
    dataToAdd.forEach((data) => {
      const icon = isSelection
        ? getSelectionIconByType(data.type)
        : getIconByType(data.type);
      const layer = isSelection
        ? selectionLayerRef.current
        : layerRef.current;
      const mark = L.marker(
        [data.coordinates.latitude, data.coordinates.longitude],
        {
          icon,
          riseOnHover: true,
          title: `${data.name} (Schoolnumber: ${data.number})`,
          alt: `${data.name} (Schoolnumber: ${data.number})`,
          name: data.name,
          id: data.number,
          opacity: isSelection ? 1.0 : 0.4,
        },
      ).addTo(layer);
      mark.bindTooltip(`${data.name} ${data.type} ${data.number}`, { direction: 'top' }, layerRef.current);
      mark.on('click', onClick);
    });
  };

  const moveMapToSelectedData = () => {
    const bounds = selectionLayerRef.current.getBounds();
    if (!hasMoved) {
      if (selectedSchools > 1) {
        mapRef.current.fitBounds(bounds);
      } else {
        const shiftedBounds = [
          selectedSchools[0].coordinates.latitude,
          selectedSchools[0].coordinates.longitude,
        ];
        mapRef.current.panTo(shiftedBounds);
      }
    }
    setHasMoved(true);
  };

  const initMap = () => {
    mapRef.current = L.map('map', {
      center: [52.5, 13.3],
      zoom: 10,
      zoomControl: false,
      preferCanvas: true,
      layers: [
        L.tileLayer('https://api.mapbox.com/styles/v1/{username}/{id}/tiles/{z}/{x}/{y}@2x?access_token={accessToken}', {
          attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> | &copy; <a href="https://www.mapbox.com/about/maps/">Mapbox</a> | <a href="https://www.mapbox.com/map-feedback/#/-74.5/40/10">Improve this map</a>',
          username: 'dride-for-sure',
          maxZoom: 18,
          id: 'cknbs9j6e0x7f17ocoqfehxv8',
          tileSize: 512,
          zoomOffset: -1,
          accessToken: 'pk.eyJ1IjoiZHJpZGUtZm9yLXN1cmUiLCJhIjoiY2tuYnJ2ZWFxMXVwMTJ4bnhnZjN3bjZiaiJ9.PM9v_F_8pqjJnhVtQZmYTg',
        })],
    });
    L.control.zoom().setPosition('bottomright').addTo(mapRef.current);
    mapRef.current.locate({ setView: true });
    mapRef.current.on('moveend', () => onBoundsChange(mapRef.current.getBounds()));
    layerRef.current = L.featureGroup().addTo(mapRef.current);
    selectionLayerRef.current = L.featureGroup().addTo(mapRef.current);
    onBoundsChange(mapRef.current.getBounds());
  };

  useEffect(() => {
    initMap();
  }, []);

  useEffect(() => {
    if (schools.length && selectedSchools.length) {
      layerRef.current.clearLayers();
      selectionLayerRef.current.clearLayers();
      removeDuplicates(schools, selectedSchools);
      addDataToMap(schools);
      addDataToMap(selectedSchools, true);
      moveMapToSelectedData();
    } else if (schools.length) {
      selectionLayerRef.current.clearLayers();
      layerRef.current.clearLayers();
      addDataToMap(schools);
    } else if (selectedSchools.length) {
      selectionLayerRef.current.clearLayers();
      addDataToMap(selectedSchools, true);
      moveMapToSelectedData();
    } else {
      selectionLayerRef.current.clearLayers();
    }
  }, [schools, selectedSchools]);

  return (
    <Wrapper>
      <Leaflet id="map" />
    </Wrapper>
  );
}

const Wrapper = styled.main`
  position: relative;
  height: 100vh;
  max-height: 800px;
  width: 100%;

  :after {
    ${BrandBar}
    position: position;
    bottom: 0;
    z-index: 0;
  }
`;

const Leaflet = styled.div`
  height: calc(100% - 4px);
  width: 100%;
  z-index: 1;
`;

Map.propTypes = {
  schools: array,
  selectedSchools: array,
  onClick: func.isRequired,
  onBoundsChange: func.isRequired,
  hasMoved: bool.isRequired,
  setHasMoved: func.isRequired,
};
