import { array, func } from 'prop-types';
import styled from 'styled-components/macro';
import MainButton from '../../buttons/BrandButton';
import HeadlineWithSubtitle from '../../headlines/HeadlineWithSubtitle';
import SideBarList from '../../lists/cms/sidebar/SideBarList';

export default function SideBar({ pages, usedTypes, onAddPage, onAddSchool, setLandingPage }) {
  return (
    <Container>
      {pages && (
        <>
          <HeadlineWithSubtitle title="Pages" subtitle="List of all pages" />
          <MainButton onClick={() => onAddPage('highlights')}>Add Hightlight Page</MainButton>
          <MainButton variant="secondary" onClick={() => onAddPage('contentfull')}>Add Content Page</MainButton>
        </>
      )}
      {usedTypes && (
        <>
          <HeadlineWithSubtitle title="Schools" subtitle="List of all schools" />
          <MainButton onClick={onAddSchool}>Add School</MainButton>
        </>
      )}
      <SideBarList pages={pages} usedTypes={usedTypes} setLandingPage={setLandingPage} />
    </Container>
  );
}

const Container = styled.div`
  > button:first-of-type{
    margin-bottom:0;
  }

  > button:last-of-type{
    margin-bottom: var(--default-margin);
  }
  
  > button+button {
    margin-top: var(--container-padding);
  }
`;

SideBar.propTypes = {
  pages: array,
  usedTypes: array,
  onAddPage: func,
  onAddSchool: func,
  setLandingPage: func,
};
