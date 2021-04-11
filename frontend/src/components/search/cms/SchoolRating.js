import styled from 'styled-components/macro';
import Rating from '../../icons/Rating';
import FlexRowStart from '../../structures/_FlexRowStart';

export default function SchoolRating() {
  const rating = 3;
  const getStars = () => {
    const stars = [];
    for (let i = 0; i < 5; i += 1) {
      if (i >= rating) {
        stars.push(<Rating variant="empty" />);
      } else {
        stars.push(<Rating variant="full" />);
      }
    }
    return stars;
  };

  return (
    <Container>
      {getStars()}
    </Container>
  );
}

const Container = styled.div`
  ${FlexRowStart}
  align-items: center;
  height: 100%;
`;
