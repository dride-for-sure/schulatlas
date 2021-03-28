import { render, screen } from '@testing-library/react';
import MainButton from './MainButton';

const createTestProps = (disabled, variant) => ({
  children: 'String',
  disabled,
  variant,
});

describe('<Button />', () => {
  it('renders the primary button component', () => {
    const props = createTestProps(false, 'primary');
    render(<MainButton {...props} />);
    const button = screen.queryByRole('button', { name: /String/i });
    const arrow = screen.queryByRole('img');

    expect(button).toBeTruthy();
    expect(button.lastChild).toBe(arrow);
    expect(button).toContainElement(arrow);
  });

  it('renders the secondary button component', () => {
    const props = createTestProps(false, 'secondary');
    render(<MainButton {...props} />);
    const button = screen.queryByRole('button', { name: /String/i });
    const arrow = screen.queryByRole('img');

    expect(button).toBeTruthy();
    expect(button).toContainElement(arrow);
  });

  it('renders the button component disabled', () => {
    const props = createTestProps(true, 'secondary');
    render(<MainButton {...props} />);
    const button = screen.queryByRole('button', { name: /String/i });
    const arrow = screen.queryByRole('img');

    expect(button).toBeDisabled();
    expect(button).toContainElement(arrow);
  });
});
