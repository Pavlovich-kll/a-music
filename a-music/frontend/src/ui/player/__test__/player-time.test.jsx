import React from 'react'
import PlayerTime from '../player-time'
import {formattedTime} from '../player-time'
import '@testing-library/jest-dom/extend-expect';
import {shallow} from "enzyme";


jest.mock('react-redux');
jest.mock('../../../components/current-track', () => 'div');

describe('PlayerTime', () => {
  let wrapper;
  const props = {
    currentTime: 120,
    duration: 240,
    onHandleChangeTime: jest.fn()
  };

  beforeEach(() => {
    wrapper = shallow(<PlayerTime {...props} />);
  });

  it('should return a valid time format', () => {
    const res = formattedTime(120);
    expect(res).toBe('02:00');
  });

  it('should return a formatted default time', () => {
    const res = formattedTime(Infinity);
    expect(res).toBe('00:00');
  });

  it('should render', () => {
    expect(wrapper.find('[data-testid="player-time"]').length).toBe(1);
  });

  it('should call a onHandleChangeTime function when we change a time on slider', () => {
    wrapper.find('[data-testid="player-slider"]').simulate('change');
    expect(props.onHandleChangeTime).toHaveBeenCalled();
  });
});
