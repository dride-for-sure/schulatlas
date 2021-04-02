import { array, func, object } from 'prop-types';
import styled from 'styled-components';
import { prettifySlug } from '../../../../common/slug';
import convertTimeStampToDate from '../../../../common/timeStamp';
import BrandButton from '../../../buttons/BrandButton';
import GridEdit from '../../../grid/cms/GridEdit';
import HeadlineWithSubtitle from '../../../headlines/HeadlineWithSubtitle';
import Loading from '../../../loading/Loading';
import FlexColumnStart from '../../../structures/_FlexColumnStart';
import SchoolAddress from './SchoolAddress';
import SchoolContact from './SchoolContact.js';
import SchoolImage from './SchoolImage';
import SchoolNumber from './SchoolNumber';
import SchoolProperty from './SchoolProperty';
import SchoolType from './SchoolTypes';

export default function EditSchool({
  tmpSchool,
  availableTypes,
  availableProperties,
  onChange,
  onAddProperty,
  onDeleteProperty,
  onSchoolDelete,
  onFileUpload,
  onFileDelete }) {
  if (!tmpSchool) {
    return <Loading />;
  }

  return (
    <GridEdit>
      <HeadlineWithSubtitle
        size="l"
        title={prettifySlug(tmpSchool.name)}
        subtitle={`Updated: ${convertTimeStampToDate(tmpSchool.updated)}, MarkOutdated: ${tmpSchool.markedOutdated}`}
        />
      <SchoolNumber
        school={tmpSchool}
        onChange={onChange} />
      <SchoolType
        school={tmpSchool}
        availableTypes={availableTypes}
        onChange={onChange} />
      <SchoolAddress
        address={tmpSchool.address}
        onChange={onChange} />
      <SchoolContact
        contact={tmpSchool.contact}
        onChange={onChange} />
      <SchoolImage
        imageUrl={tmpSchool.image}
        onFileUpload={onFileUpload}
        onFileDelete={onFileDelete} />
      {tmpSchool.properties.map((property) => (
        <SchoolProperty
          key={property.name}
          availableProperties={availableProperties}
          onDeleteProperty={onDeleteProperty}
          property={property}
          onChange={onChange} />
      ))}
      <BottomButtons>
        <BrandButton onClick={onAddProperty}>Add Property</BrandButton>
        <BrandButton variant="monochrome" onClick={() => onSchoolDelete(tmpSchool.number)}>Delete School</BrandButton>
      </BottomButtons>
    </GridEdit>
  );
}

const BottomButtons = styled.div`
  ${FlexColumnStart};

  > button {
    margin: 0;

    + button {
      margin-top: var(--container-padding);
    }
  }
`;

EditSchool.propTypes = {
  tmpSchool: object.isRequired,
  availableTypes: array.isRequired,
  availableProperties: array.isRequired,
  onAddProperty: func.isRequired,
  onDeleteProperty: func.isRequired,
  onChange: func.isRequired,
  onSchoolDelete: func.isRequired,
  onFileUpload: func.isRequired,
  onFileDelete: func.isRequired,
};
