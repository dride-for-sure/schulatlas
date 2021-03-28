import { array, func } from 'prop-types';
import styled from 'styled-components';
import MainButton from '../../buttons/MainButton';
import HeadlineWithSubtitle from '../../headlines/HeadlineWithSubtitle';
import SideBarList from '../../lists/cms/sidebar/SideBarList';

export default function SideBar({ pages, types, onAddPage, onAddSchool }) {
  return (
    <Container>
      {pages && (
        <>
          <HeadlineWithSubtitle title="Pages" subtitle="List of all pages" />
          <MainButton onClick={onAddPage}>Add Page</MainButton>
        </>
      )}
      {types && (
        <>
          <HeadlineWithSubtitle title="Schools" subtitle="List of all schools" />
          <MainButton onClick={onAddSchool}>Add School</MainButton>
        </>
      )}
      <SideBarList pages={pages} types={types} />
    </Container>
  );
}

const Container = styled.div``;

SideBar.propTypes = {
  pages: array,
  types: array,
  onAddPage: func.isRequired,
  onAddSchool: func.isRequired,
};
