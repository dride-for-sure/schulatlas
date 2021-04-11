import styled from 'styled-components/macro';
import Button from '../../buttons/_Button';
import RegularLink from '../../links/RegularLink';

export default function SearchBarCallForHelp() {
  return (
    <Container>
      <RegularLink to="https://github.com/dride-for-sure/schulatlas" title="Link to github profile">
        <span>
          Daten unvollständig oder fehlerhaft?
          {' '}
          <b>Hilf mit!</b>
        </span>
      </RegularLink>
    </Container>
  );
}

const Container = styled.div`
  > a {
    ${Button}
    justify-content: flex-start;
    color: white;
    font-size: .65rem;
    padding: 0.2rem var(--default-padding-s);
    width: 100%;
    height: fit-content;
    border-radius: 0;
    margin: 0;

    :after {
      border-radius: 0;
    }
  }
`;
