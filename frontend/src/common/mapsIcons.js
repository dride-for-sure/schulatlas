import L from 'leaflet';
import PinBeige from '../resources/images/PinBeige.png';
import PinBeige2x from '../resources/images/PinBeige@2x.png';
import PinGreen from '../resources/images/PinGreen.png';
import PinGreen2x from '../resources/images/PinGreen@2x.png';
import PinPink from '../resources/images/PinPink.png';
import PinPink2x from '../resources/images/PinPink@2x.png';
import PinPurple from '../resources/images/PinPurple.png';
import PinPurple2x from '../resources/images/PinPurple@2x.png';
import PinRed from '../resources/images/PinRed.png';
import PinRed2x from '../resources/images/PinRed@2x.png';
import PinWhite from '../resources/images/PinWhite.png';
import PinWhite2x from '../resources/images/PinWhite@2x.png';

const getIconUrlByType = (type) => {
  switch (type.toLowerCase()) {
    case 'grundschule':
      return {
        normal: PinPink,
        retina: PinPink2x,
      };
    case 'realschule':
      return {
        normal: PinGreen,
        retina: PinGreen2x,
      };
    case 'gymnasium':
      return {
        normal: PinPurple,
        retina: PinPurple2x,
      };
    case 'integrierte gesamtschule':
      return {
        normal: PinBeige,
        retina: PinBeige2x,
      };
    case 'gesamtschule':
      return {
        normal: PinRed,
        retina: PinRed2x,
      };
    default:
      return {
        normal: PinWhite,
        retina: PinWhite2x,

      };
  }
};

export const getIconByType = (type) => L.icon({
  iconUrl: getIconUrlByType(type).normal,
  iconRetinaUrl: getIconUrlByType(type).retina,
  iconSize: [25, 45],
  iconAnchor: [12.5, 45],
  tooltipAnchor: [0, -50],
});

export const getSelectionIconByType = (type) => L.icon({
  iconUrl: getIconUrlByType(type).normal,
  iconRetinaUrl: getIconUrlByType(type).retina,
  iconSize: [25, 45],
  iconAnchor: [12.5, 45],
  tooltipAnchor: [0, -50],
});
