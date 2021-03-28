import { array, func } from 'prop-types';
import styled from 'styled-components/macro';
import MainButton from '../../buttons/MainButton';
import HeadlineWithSubtitle from '../../headlines/HeadlineWithSubtitle';
import SideBarList from '../../lists/cms/sidebar/SideBarList';

export default function SideBar({ pages, types, onAddPage, onAddSchool, setLandingPage }) {
  return (
    <Container>
      {pages && (
        <>
          <HeadlineWithSubtitle title="Pages" subtitle="List of all pages" />
          <MainButton onClick={() => onAddPage('highlights')}>Add Hightlight Page</MainButton>
          <MainButton variant="secondary" onClick={() => onAddPage('contentfull')}>Add Content Page</MainButton>
        </>
      )}
      {types && (
        <>
          <HeadlineWithSubtitle title="Schools" subtitle="List of all schools" />
          <MainButton onClick={onAddSchool}>Add School</MainButton>
        </>
      )}
      <SideBarList pages={pages} types={types} setLandingPage={setLandingPage} />
    </Container>
  );
}

const Container = styled.div`
  > button:first-of-type{
    margin-bottom:0;
  }

  > button+button {
    margin-top: calc(var(--default-margin) * 0.5);
  }
`;

SideBar.propTypes = {
  pages: array,
  types: array,
  onAddPage: func,
  onAddSchool: func,
  setLandingPage: func,
};
