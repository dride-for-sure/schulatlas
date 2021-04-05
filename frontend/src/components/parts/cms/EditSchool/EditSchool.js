import { array, func, object } from 'prop-types';
import { prettifySlug } from '../../../../common/slug';
import convertTimeStampToDate from '../../../../common/timeStamp';
import BrandButton from '../../../buttons/BrandButton';
import HeadlineWithSubtitle from '../../../headlines/HeadlineWithSubtitle';
import Loading from '../../../loading/Loading';
import GridEdit from '../../../structures/GridEdit';
import SchoolAddress from './SchoolAddress';
import SchoolContact from './SchoolContact.js';
import SchoolImage from './SchoolImage';
import SchoolName from './SchoolName';
import SchoolNumber from './SchoolNumber';
import SchoolProperty from './SchoolProperty';
import SchoolType from './SchoolTypes';

export default function EditSchool({
  school,
  availableTypes,
  availableProperties,
  onChange,
  onAddProperty,
  onDeleteProperty,
  onSchoolDelete,
  onFileUpload,
  onFileDelete }) {
  if (!school) {
    return <Loading />;
  }

  return (
    <GridEdit>
      <HeadlineWithSubtitle
        size="l"
        title={prettifySlug(school.name)}
        subtitle={`Updated on ${convertTimeStampToDate(school.updated)} by ${school.userId}, MarkOutdated: ${school.markedOutdated}`}
        />
      <SchoolName
        school={school}
        onChange={onChange} />
      <SchoolNumber
        school={school}
        onChange={onChange} />
      <SchoolType
        school={school}
        availableTypes={availableTypes}
        onChange={onChange} />
      <SchoolAddress
        address={school.address}
        onChange={onChange} />
      <SchoolContact
        contact={school.contact}
        onChange={onChange} />
      <SchoolImage
        imageUrl={school.image}
        onFileUpload={onFileUpload}
        onFileDelete={onFileDelete} />
      {school.properties.map((property) => (
        <SchoolProperty
          key={property.name}
          availableProperties={availableProperties}
          onDeleteProperty={onDeleteProperty}
          property={property}
          onChange={onChange} />
      ))}
      <BrandButton onClick={onAddProperty}>Add Property</BrandButton>
      <BrandButton variant="monochrome" onClick={() => onSchoolDelete(school.number)}>Delete School</BrandButton>
    </GridEdit>
  );
}

EditSchool.propTypes = {
  school: object.isRequired,
  availableTypes: array.isRequired,
  availableProperties: array.isRequired,
  onAddProperty: func.isRequired,
  onDeleteProperty: func.isRequired,
  onChange: func.isRequired,
  onSchoolDelete: func.isRequired,
  onFileUpload: func.isRequired,
  onFileDelete: func.isRequired,
};
