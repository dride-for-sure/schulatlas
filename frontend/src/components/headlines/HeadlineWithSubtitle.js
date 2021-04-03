import { bool, string } from 'prop-types';
import styled, { css } from 'styled-components/macro';

const HeadlineWithSubtitle = ({ title, subtitle, size, margin }) => (
  <Container>
    <Title size={size}>{title}</Title>
    <Subtitle size={size} margin={margin}>{subtitle}</Subtitle>
  </Container>
);

const Container = styled.div`
  > * {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
`;

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

  ${(props) => props.margin === false && css`
    margin: var(--margin-subtitle);
  `}
  
  ${(props) => props.size === 'l' && css`
    font-size: var(--font-size-subtitle-l);
  `}
`;

HeadlineWithSubtitle.propTypes = {
  title: string.isRequired,
  subtitle: string.isRequired,
  size: string,
  margin: bool,
};

export default HeadlineWithSubtitle;
