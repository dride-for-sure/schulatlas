import { object } from 'prop-types';
import styled from 'styled-components/macro';
import BrandBar from '../../brandBar/_BrandBar';
import Image from '../../image/Image';
import SchoolDetailsList from '../../lists/maps/SchoolDetailsList';
import SchoolDetailsHeader from './SchoolDetailsHeader';
import SchoolDetailsSummary from './SchoolDetailsSummary';
import SearchBarCallForHelp from './SearchDetailsCallForHelp';

export default function SearchDetails({ schoolDetails }) {
  if (!schoolDetails) {
    return null;
  }

  return (
    <Wrapper>
      <ImageContainer>
        <Image
          src={schoolDetails.image === null ? '' : schoolDetails.image}
          alt={`Bilder der ${schoolDetails.name} in ${schoolDetails.city}`} />
      </ImageContainer>
      <MaxHeightContainer>
        <PaddingContainer>
          <SchoolDetailsHeader schoolDetails={schoolDetails} />
          <SchoolDetailsSummary schoolDetails={schoolDetails} />
          <SchoolDetailsList schoolDetails={schoolDetails} />
        </PaddingContainer>
      </MaxHeightContainer>
      <SearchBarCallForHelp />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: absolute;
  z-index: 3;
  box-sizing: border-box;
  top: 38px;
  width: 300px;
  border-radius: var(--border-radius);
  background-color: white;
  backdrop-filter: blur(20px);
  box-shadow: var(--box-shadow);
  overflow: hidden;
  overflow-y: scroll;
`;

const ImageContainer = styled.div`
  display: flex;
  position: relative;
  width: 100%;

  > img {
    height: 180px;
    object-fit: cover;
  }

  :after {
    ${BrandBar}
    bottom: 0;
  }
`;

const MaxHeightContainer = styled.div`
  max-height: 350px;
  overflow: hidden;
  overflow-y: scroll;
`;

const PaddingContainer = styled.div`
  padding: calc(var(--default-padding-s) * 0.5) var(--default-padding-s) calc(var(--default-padding-s) * 0.9);
`;

SearchDetails.propTypes = {
  schoolDetails: object.isRequired,
};
