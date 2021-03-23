import { node } from 'prop-types';
import Footer from '../../footer/cms/Footer';
import Header from '../../header/cms/Header';

export default function CmsWrapper({ children }) {
  return (
    <>
      <Header />
      {children}
      <Footer />
    </>
  );
}

CmsWrapper.propTypes = {
  children: node.isRequired,
};
