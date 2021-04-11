import { string } from 'prop-types';
import styled from 'styled-components/macro';
import RatingEmpty from '../../resources/images/RatingEmpty.png';
import RatingFull from '../../resources/images/RatingFull.png';
import RatingHalf from '../../resources/images/RatingHalf.png';

export default function Rating({ variant }) {
  const getImage = () => {
    switch (variant) {
      case 'empty':
        return RatingEmpty;
      case 'half':
        return RatingHalf;
      default:
        return RatingFull;
    }
  };

  return (
    <Image src={getImage()} alt="Schoolrating" />
  );
}

const Image = styled.img`
  height: 10px;
`;

Rating.propTypes = {
  variant: string.isRequired,
};
