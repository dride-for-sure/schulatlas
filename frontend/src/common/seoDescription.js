import { Helmet } from 'react-helmet';

const getSeoTitle = (page) => {
  try {
    return page.assemblies.find((assembly) => assembly.type === 'seo')
      .components.find((component) => component.type === 'title').content;
  } catch (e) {
    return '';
  }
};

const getSeoDescription = (page) => {
  try {
    return page.assemblies.find((assembly) => assembly.type === 'seo')
      .components.find((component) => component.type === 'description').content;
  } catch (e) {
    return '';
  }
};

const getHeroImage = (page) => {
  try {
    return page.assemblies.find((assembly) => assembly.type === 'hero')
      .components.find((component) => component.type === 'image');
  } catch (e) {
    return '';
  }
};

const getPageSeoTags = (page) => (
  <Helmet>
    <title>{page ? getSeoTitle(page) : 'SCHULATLAS'}</title>
    <meta name="description" content={`${getSeoDescription(page)}`} />
    <meta property="og:type" content="article" />
    <meta property="og:title" content={getSeoTitle(page)} />
    <meta property="og:description" content={`${getSeoDescription(page)}`} />
    <meta property="og:image" content={getHeroImage(page).url} />
    <meta property="og:url" content={window.location.href} />
    <meta property="og:site_name" content="SCHULATLAS" />
    <meta name="twitter:title" content={getSeoTitle(page)} />
    <meta name="twitter:description" content={`${getSeoDescription(page)}`} />
    <meta name="twitter:image" content={getHeroImage(page).url} />
  </Helmet>
);

export default getPageSeoTags;
