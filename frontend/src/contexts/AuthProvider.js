import decode from 'jwt-decode';
import { node } from 'prop-types';
import { createContext, useContext, useEffect, useState } from 'react';
import axiosConfig from '../config/axiosConfig';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState('');

  const isTokenExpired = (tokenToTest) => {
    try {
      const decodedToken = decode(tokenToTest);
      return decodedToken.exp < Date.now() / 1000;
    } catch (error) {
      return false;
    }
  };

  useEffect(() => {
    const possibleToken = sessionStorage.getItem('schulatlas');
    if (possibleToken && !isTokenExpired(possibleToken)) {
      setToken(possibleToken);
    }
  }, []);

  useEffect(() => {
    if (token) {
      axiosConfig.setAuthToken(token);
      sessionStorage.setItem('schulatlas', token);
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
