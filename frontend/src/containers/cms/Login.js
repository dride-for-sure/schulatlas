import { useState } from 'react';
import { Redirect } from 'react-router-dom';
import styled from 'styled-components/macro';
import MainButton from '../../components/buttons/MainButton';
import Input from '../../components/form/Input';
import Headline from '../../components/headlines/Headline';
import Logo from '../../components/logo/Logo';
import FlexColumnCenter from '../../components/structures/FlexColumnCenter';
import FlexRowCenter from '../../components/structures/FlexRowCenter';
import { useAuth } from '../../contexts/AuthProvider';
import login from '../../services/api/private/loginApiService';

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
    <Wrapper>
      <Container>
        <Logo />
        <Headline size="l">Please login:</Headline>
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
          <MainButton disabled={!username || !password}>Continue</MainButton>
        </Form>
      </Container>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  position: absolute;
  width: 100%;
  height: 100vh;
  ${FlexRowCenter}
`;

const Container = styled.div`
  ${FlexColumnCenter}
  align-self: center;
  height: fit-content;
  margin-bottom: 20vh;

  // Logo
  > a:first-of-type {
    margin: 30px;
  }

  > * {
    align-self: center;
  }
`;

const Form = styled.form`
  ${FlexColumnCenter}

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
