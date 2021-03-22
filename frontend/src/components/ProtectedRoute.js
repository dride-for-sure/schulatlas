import { bool, string } from 'prop-types';
import { Redirect, Route } from 'react-router-dom';
import { useAuth } from '../contexts/AuthProvider';

export default function ProtectedRoute({ exact, path }) {
  const { token } = useAuth();

  return (
    token ? <Route exact={exact} path={path} /> : <Redirect to="/cms/login" />
  );
}

ProtectedRoute.propTypes = {
  exact: bool.isRequired,
  path: string.isRequired,
};
