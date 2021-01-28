import React from "react";
import Enzyme, {shallow} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import AdditionalControlsPanel from "../player-additional-controls-panel";


Enzyme.configure({ adapter: new Adapter() });
jest.mock('react-redux');

describe('AdditionalControlsPanel', () => {
  let wrapper;
  let mockHandleClick = jest.fn();

  const props = {
    volume: 10,
    onChange: mockHandleClick,
    loop: false,
    onToggleLoop: mockHandleClick,
    likes: 10
  }

  beforeEach(() => {
    wrapper = shallow(<AdditionalControlsPanel {...props} />);
  });

  it('should render', () =>{
    expect(wrapper.find('[data-testid="loop"]').length).toBe(1);
  });

  it ('should call a function after click on Loop', () => {
    wrapper.find('[data-testid="loop"]').simulate('click');
    expect(props.onToggleLoop).toHaveBeenCalledTimes(1);
  });

  it('should have loop prop equal false', () => {
    expect(wrapper.props().loop).toEqual(false);
  });

  it('should have loop prop equal by default value false', () => {
    wrapper.setProps({ loop: undefined });
    expect(wrapper.props().loop).toEqual(false);
  });

  it('should render RepeaIcon', () => {
    expect(wrapper.find('[data-testid="repeat-icon"]').length).toBe(1);
  });

  it('should render RepeatOneIcon', () => {
    wrapper.setProps({ loop: true });
    expect(wrapper.find('[data-testid="repeat-one-icon"]').length).toBe(1);
  });
});
