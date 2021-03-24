import { string } from 'prop-types';
import styled, { css } from 'styled-components/macro';

const HeadlineWithSubtitle = ({ title, subtitle, size }) => (
  <>
    <Title size={size}>{title}</Title>
    <Subtitle size={size}>{subtitle}</Subtitle>
  </>
);

const Title = styled.h2`
  font-family: var(--font-family-title);
  font-size: var(--font-size-title-m);
  font-weight: var(--font-weight-title);

  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-title-l);
  `}
`;

const Subtitle = styled.span`
  display: block;
  font-family: var(--font-family-subtitle);
  font-size: var(--font-size-subtitle-m);
  font-style: var(--font-style-subtitle);

  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-subtitle-l);
  `}

  margin: var(--margin-subtitle);
`;

HeadlineWithSubtitle.propTypes = {
  title: string.isRequired,
  subtitle: string.isRequired,
  size: string,
};

export default HeadlineWithSubtitle;
