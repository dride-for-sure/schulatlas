import React from 'react';
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import GlobalStyle from './components/GlobalStyles';
import FeatureNotAvailable from './components/mvp/FeatureNotAvailable';
import ProtectedRoute from './components/ProtectedRoute';
import ScrollToTop from './components/scroll/ScrollToTop';
import Login from './containers/cms/Login';
import Pages from './containers/cms/Pages';
import Schools from './containers/cms/Schools';
import Maps from './containers/schulatlas/Maps';
import Page from './containers/schulatlas/Page';
import { AuthProvider } from './contexts/AuthProvider';
import './fonts.css';

function App() {
  return (
    <Router>
      <AuthProvider>
        <ScrollToTop />
        <GlobalStyle />
        <Switch>
          <Route path="/cms/login" exact>
            <Login />
          </Route>
          <ProtectedRoute path="/cms/school/:number" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/schools/search/:searchFor" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/schools" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/type/:type" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/page/:slug" exact>
            <Pages />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/pages" exact>
            <Pages />
          </ProtectedRoute>
          <ProtectedRoute path="/cms" exact>
            <Redirect to="/cms/pages" />
          </ProtectedRoute>

          <Route path="/maps/type/:type" exact>
            <Maps />
          </Route>
          <Route path="/maps/school/:number" exact>
            <Maps />
          </Route>
          <Route path="/maps/search/:searchFor" exact>
            <Maps />
          </Route>
          <Route path="/maps" exact>
            <Maps />
          </Route>
          <Route path="/:slug?" exact>
            <Page />
          </Route>

          <Route path="/featurenotavailable" exact>
            <FeatureNotAvailable />
          </Route>
        </Switch>
      </AuthProvider>
    </Router>
  );
}

export default App;
