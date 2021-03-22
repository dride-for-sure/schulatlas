import { node } from 'prop-types';
import Footer from '../Footer';
import Header from '../Header';

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
