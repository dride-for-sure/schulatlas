import { useState } from 'react';
import { Redirect } from 'react-router-dom';
import styled from 'styled-components/macro';
import ButtonPrimary from '../../components/buttons/Button';
import Input from '../../components/forms/Input';
import H1 from '../../components/headlines/H1';
import Logo from '../../components/logo/Logo';
import { useAuth } from '../../contexts/AuthProvider';
import login from '../../services/private/loginApiService';

export default function Login() {
  const { token, setToken } = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  if (token) {
    return <Redirect to="/cms/pages" />;
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!username && !password) {
      return;
    }
    login(username, password)
      .then(setToken)
      .catch((error) => console.log(error));
    setUsername('');
    setPassword('');
  };

  return (
    <Absolute>
      <Container>
        <Logo />
        <H1 size="l">Please login:</H1>
        <Form onSubmit={handleSubmit}>
          <Input
            placeholder="username..."
            type="text"
            value={username}
            onChange={({ target }) => setUsername(target.value)} />
          <Input
            placeholder="password..."
            type="password"
            value={password}
            onChange={({ target }) => setPassword(target.value)} />
          <ButtonPrimary>Continue</ButtonPrimary>
        </Form>
      </Container>
    </Absolute>
  );
}

const Absolute = styled.div`
  position: absolute;
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
  justify-content: center;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-self: center;
  height: fit-content;
  margin-bottom: 20vh;

  // Logo
  > span:first-of-type {
    margin: 30px;
  }

  > * {
    align-self: center;
  }
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;

  > * {
    align-self: center;
  }

  > input + input {
    margin-top: 10px;
  }

  > button {
    margin-top: 30px;
  }
`;
