import { object } from 'prop-types';
import styled from 'styled-components/macro';
import Headline from '../../headlines/Headline';
import HeadlineWithSubtitle from '../../headlines/HeadlineWithSubtitle';

export default function EditPage({ page }) {
  const getTitle = () => {
    try {
      return page.name;
    } catch (e) {
      return '';
    }
  };

  const getSubtitle = () => {
    try {
      return page.subtitle;
    } catch (e) {
      return '';
    }
  };

  return (
    <Container>
      {page.landingPage && <HeadlineWithSubtitle title={getTitle()} subtitle={getSubtitle()} />}
      {!page.landingPage && <Headline size="m">{getTitle()}</Headline>}
    </Container>
  );
}

const Container = styled.div``;

EditPage.propTypes = {
  page: object,
};

/*
private String name;
 private Long updated;
 private String userId;
 private boolean landingPage;
 private List<Assembly> assemblies;
 */
