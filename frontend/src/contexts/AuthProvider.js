import { node } from 'prop-types';
import { createContext, useContext, useEffect, useState } from 'react';
import axiosConfig from '../config/axiosConfig';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState('');

  useEffect(() => {
    if (token) {
      axiosConfig.setAuthToken(token);
    }
  }, [token]);

  return (
    <AuthContext.Provider value={{ token, setToken }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);

AuthProvider.propTypes = {
  children: node.isRequired,
};
