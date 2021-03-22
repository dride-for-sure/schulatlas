import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import GlobalStyle from './components/GlobalStyles';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './containers/cms/login/Login';
import PageDetails from './containers/cms/page/PageDetails';
import PageList from './containers/cms/page/PageList';
import SchoolDetails from './containers/cms/school/SchoolDetails';
import SchoolList from './containers/cms/school/SchoolList';
import SchoolSearchResults from './containers/cms/school/SchoolSearchResults';
import Maps from './containers/schulatlas/Maps';
import Page from './containers/schulatlas/Page';
import { AuthProvider } from './contexts/AuthProvider';
import './fonts.css';

function App() {
  return (
    <Router>
      <AuthProvider>
        <GlobalStyle />
        <Switch>
          <Route path="/cms/login" exact>
            <Login />
          </Route>
          <ProtectedRoute path="/cms/schools/search/:search" exact>
            <SchoolSearchResults />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/schools/:number" exact>
            <SchoolDetails />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/schools" exact>
            <SchoolList />
          </ProtectedRoute>

          <ProtectedRoute path="/cms/pages/:name" exact>
            <PageDetails />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/pages" exact>
            <PageList />
          </ProtectedRoute>
          <ProtectedRoute path="/cms" exact>
            <PageDetails />
          </ProtectedRoute>

          <Route path="/entdecken" exact>
            <Maps />
          </Route>
          <Route path="/:name?" exact>
            <Page />
          </Route>
        </Switch>
      </AuthProvider>
    </Router>
  );
}

export default App;
