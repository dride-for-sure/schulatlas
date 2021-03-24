import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Input from './Input';

const onChange = jest.fn();

const createTestProps = () => ({
  placeholder: 'String',
  type: 'password',
  value: 'Value',
  onChange,
});

describe('<Input />', () => {
  it('renders the input component with text centered', () => {
    const props = createTestProps();
    render(<Input {...props} />);
    const input = screen.queryByPlaceholderText('String');

    expect(input).toBeTruthy();
    expect(input).toHaveValue('Value');
  });

  it('renders the input component with text left', () => {
    const props = createTestProps();
    render(<Input {...props} />);
    const input = screen.queryByPlaceholderText('String');

    expect(input).toBeTruthy();
    expect(input).toHaveValue('Value');
  });

  it('user types inside the input component', () => {
    const props = createTestProps();
    render(<Input {...props} />);
    const input = screen.queryByPlaceholderText('String');

    expect(input).toHaveValue('Value');
    userEvent.type(input, 'updatedValue');
    expect(onChange).toHaveBeenCalled();
  });
});
