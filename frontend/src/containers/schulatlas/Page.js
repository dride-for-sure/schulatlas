import { useParams } from 'react-router-dom';

export default function Page() {
  const { name } = useParams();

  return (
    <>
      {name && <p>{`Page ${name}`}</p>}
      {!name && <p>LandingPage</p>}
    </>
  );
}
