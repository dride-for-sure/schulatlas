/* eslint-disable react/forbid-prop-types */
/* eslint-disable react/jsx-props-no-spreading */
import { object } from 'prop-types';
import { Redirect, Route } from 'react-router-dom';
import { useAuth } from '../contexts/AuthProvider';

export default function ProtectedRoute(props) {
  const { token } = useAuth();

  return (
    token ? <Route {...props} /> : <Redirect to="/cms/login" />
  );
}

ProtectedRoute.propTypes = {
  props: object,
};
