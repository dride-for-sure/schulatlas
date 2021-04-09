import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import getPageSeoTags from '../../common/seoDescription';
import CardList from '../../components/assemblies/schulatlas/card/CardList';
import CTA from '../../components/assemblies/schulatlas/cta/CTA';
import FeatureCard from '../../components/assemblies/schulatlas/featureCard/FeatureCard';
import Footer from '../../components/assemblies/schulatlas/footer/Footer';
import Sponsors from '../../components/assemblies/schulatlas/sponsors/Sponsors';
import TextBlock from '../../components/assemblies/schulatlas/textblock/TextBlock';
import Header from '../../components/header/schulatlas/Header';
import Loading from '../../components/loading/Loading';
import { getLandingPage, getPageBySlug } from '../../services/api/public/pageApiService';

export default function Page() {
  const [page, setPage] = useState(null);
  const { slug } = useParams();

  const getHero = () => page.assemblies.find((assembly) => assembly.type === 'hero');

  const getAssembly = (assembly, index) => {
    switch (assembly.type) {
      case 'sponsors':
        return <Sponsors key={index} assembly={assembly} />;
      case 'feature-card':
        return <FeatureCard key={index} assembly={assembly} />;
      case 'cards':
        return <CardList key={index} assembly={assembly} />;
      case 'cta':
        return <CTA key={index} assembly={assembly} />;
      case 'textblock':
        return <TextBlock key={index} assembly={assembly} />;
      default:
        return null;
    }
  };

  useEffect(() => {
    if (!slug) {
      getLandingPage()
        .then(setPage)
        .catch((error) => console.log(error));
    } else {
      getPageBySlug(slug)
        .then(setPage)
        .catch((error) => console.log(error));
    }
  }, [slug]);

  if (!page) {
    return <Loading />;
  }

  return (
    <>
      {getPageSeoTags(page)}
      <Header hero={getHero()} />
      {page.assemblies.map((assembly, index) => getAssembly(assembly, index))}
      <Footer />
    </>
  );
}
