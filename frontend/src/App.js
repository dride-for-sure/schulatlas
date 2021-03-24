import React from 'react';
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import GlobalStyle from './components/GlobalStyles';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './containers/cms/Login';
import Page from './containers/cms/Page';
import Schools from './containers/cms/Schools';
import Maps from './containers/schulatlas/Maps';
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
          <ProtectedRoute path="/cms/school/:number" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/schools/search/:search" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/schools" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/type/:type" exact>
            <Schools />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/page/:name" exact>
            <Page />
          </ProtectedRoute>
          <ProtectedRoute path="/cms/pages" exact>
            <Page />
          </ProtectedRoute>
          <ProtectedRoute path="/cms" exact>
            <Redirect to="/cms/pages" />
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
