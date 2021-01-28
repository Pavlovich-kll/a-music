import React from 'react';
import SearchPanel from '../index';
import { shallow } from 'enzyme';
import AsyncSelect from 'react-select/async';

const mockHandleChange = jest.fn();
let wrapper: any;

describe('SearchPanelShallow', () => {
  beforeEach(() => {
    wrapper = shallow(<SearchPanel setToPlay={mockHandleChange} />);
  });

  test('should render', () => {
    expect(wrapper).toMatchSnapshot();
  });

  test('should trigger setToPlay function', () => {
    const select = wrapper.find(AsyncSelect);
    select.props().onChange({ value: 'some value' });
    expect(mockHandleChange).toBeCalled();
  });
});
