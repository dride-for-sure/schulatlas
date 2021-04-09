import decode from 'jwt-decode';
import { useEffect, useRef, useState } from 'react';
import { Helmet } from 'react-helmet';
import { useHistory, useParams } from 'react-router-dom';
import styled from 'styled-components/macro';
import { addIndicesToNestedData, updateNestedData } from '../../common/indexData';
import { escapeSlug, prettifySlug } from '../../common/slug';
import sortPages from '../../common/sortPages';
import throttle from '../../common/throttle';
import Header from '../../components/header/cms/Header';
import PaddingContainerM from '../../components/padding/_PaddingContainerM';
import EditPage from '../../components/parts/cms/EditPage/EditPage';
import SideBar from '../../components/parts/cms/SideBar';
import FlexRowCenter from '../../components/structures/_FlexRowCenter';
import GridSideBar from '../../components/structures/_GridSideBar';
import MaxWidthL from '../../components/structures/_MaxWidthL';
import { getPageTemplate } from '../../config/schulatlasConfig';
import { useAuth } from '../../contexts/AuthProvider';
import { addAttachment } from '../../services/api/private/attachmentApiService';
import { addPage, deletePageBySlug, getPageBySlug, listPages, setLandingPageBySlug, updatePage } from '../../services/api/private/pageApiService';

export default function PageDetails() {
  const [page, setPage] = useState(null);
  const [pages, setPages] = useState([]);
  const [timer, setTimer] = useState(null);
  const [isNewPage, setIsNewPage] = useState(false);
  const [currentSlug, setCurrentSlug] = useState('');
  const pageRef = useRef(page);
  const history = useHistory();
  const { slug } = useParams();
  const { token } = useAuth();

  const getPageList = () => {
    listPages()
      .then((incomingPages) => setPages(sortPages(incomingPages)))
      .catch((error) => console.log(error));
  };

  const setLandingPage = (slugToSetLandingPage) => {
    setLandingPageBySlug(slugToSetLandingPage)
      .then(getPageList)
      .catch((error) => console.log(error));
  };

  const handleParamsUpdate = () => {
    setIsNewPage(false);
    if (slug) {
      getPageBySlug(slug)
        .then((incomingPage) => setPage(addIndicesToNestedData(incomingPage)))
        .then(setCurrentSlug(slug))
        .catch((error) => console.log(error));
    } else {
      setPage('');
    }
  };

  const handleDelete = () => {
    deletePageBySlug(slug);
    setPage('');
    setTimeout(getPageList, 1000);
    history.push('/cms/pages');
  };

  const handleSave = () => {
    const save = () => {
      const pageToSave = pageRef.current;
      if (isNewPage) {
        addPage(pageToSave)
          .then(setCurrentSlug(pageToSave.slug))
          .then(setIsNewPage(false))
          .then(setTimeout(getPageList, 1000))
          .catch((error) => console.log(error));
      } else {
        updatePage(pageToSave, currentSlug)
          .then(setCurrentSlug(pageToSave.slug))
          .then(setTimeout(getPageList, 1000))
          .catch((error) => console.log(error));
      }
    };
    throttle(() => save(), timer, setTimer);
  };

  const updateEntry = (id, entry) => {
    const user = decode(token);
    const updatedPage = {
      ...updateNestedData(page, id, entry),
      updated: Date.now(),
      userId: user.sub };
    setPage(updatedPage);
    handleSave();
  };

  const updateSlug = (event) => {
    const escapedSlug = escapeSlug(event.target.value);
    const user = decode(token);
    const updatedPage = { ...page, updated: Date.now(), userId: user.sub, slug: escapedSlug };
    setPage(updatedPage);
    handleSave();
  };

  const addNewPage = (template) => {
    const pageFromTemplate = getPageTemplate(template);
    const randomIndex = Math.floor(Math.random() * 1000);
    setIsNewPage(true);
    setPage(addIndicesToNestedData({ ...pageFromTemplate, slug: `${pageFromTemplate.slug}-${randomIndex}` }));
  };

  const uploadFile = (id, event) =>
    addAttachment(event.target.files[0])
      .then((response) => updateEntry(id, { url: response.url }))
      .catch((error) => console.error(error));

  const deleteFile = (id) => {
    updateEntry(id, { url: '' });
  };

  useEffect(() => {
    pageRef.current = page;
  }, [page]);

  useEffect(() => {
    handleParamsUpdate();
  }, [slug]);

  useEffect(() => {
    getPageList();
  }, []);

  return (
    <>
      <Helmet>
        <title>{page ? `SCHULATLAS CMS | Edit Page: "${prettifySlug(page.slug)}"` : 'SCHULATLAS CMS | Pages Overview'}</title>
      </Helmet>
      <Header />
      <Wrapper>
        <PaddingContainer>
          <MaxWidthContainer>
            {pages && (
              <SideBar
                pages={pages}
                onAddPage={addNewPage}
                setLandingPage={setLandingPage} />
            )}
            {page && (
              <EditPage
                page={page}
                pages={pages}
                onChange={updateEntry}
                onUpdateSlug={updateSlug}
                onDeletePage={handleDelete}
                onUploadFile={uploadFile}
                onDeleteFile={deleteFile} />
            )}
          </MaxWidthContainer>
        </PaddingContainer>
      </Wrapper>
    </>
  );
}

const Wrapper = styled.main``;

const PaddingContainer = styled.div`
  ${FlexRowCenter}
  ${PaddingContainerM}
`;

const MaxWidthContainer = styled.div`
  ${MaxWidthL}
  ${GridSideBar};
`;
