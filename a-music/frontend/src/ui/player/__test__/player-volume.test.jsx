import React from "react";
import Enzyme, {mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import PlayerVolume from "../player-volume";
import {fireEvent, render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';


Enzyme.configure({ adapter: new Adapter() });

describe('PlayerVolume', () => {
  let wrapper;
  const props = {
    volume: 10,
    onChange: jest.fn(),
  }

  beforeEach(() => {
    wrapper = mount(<PlayerVolume {...props} />);
  });

  it('should render with props', () => {
    expect(wrapper.find('[data-testid="volume-button"]').exists()).toBeTruthy();
  });

  it('should change volume icon after set the volume value equal 0', () => {
    expect(wrapper.find('[data-testid="volume-up"]').exists()).toBeTruthy();
    wrapper.setProps({volume: 0});
    expect(wrapper.find('[data-testid="volume-off"]').exists()).toBeTruthy();
  });

  it('should change volume icon', () => {
    const {getByTestId} = render(<PlayerVolume {...props}/>);
    fireEvent.click(getByTestId('volume-button'));
    expect(props.onChange).toBeCalled();
  });
});
