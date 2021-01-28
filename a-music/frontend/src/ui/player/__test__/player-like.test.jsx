import React from "react";
import Enzyme, {shallow} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import {LikeButton} from "../player-like";
import {useDispatch} from "react-redux";


Enzyme.configure({ adapter: new Adapter() });
jest.mock('react-redux');

describe('LikeButton', () => {
  let wrapper;

  useDispatch.mockReturnValue(
    jest.fn(() => {
      return Promise.resolve(10).then(data => data);
    })
  );

  beforeEach(() => {
    wrapper = shallow(<LikeButton likes={10} />);
  });

  it('should render', () => {
    expect(wrapper.find('[data-testid="like-component"]').length).toBe(1);
  });

  it('should fire dispatch after the click', () => {
    wrapper.find('[data-testid="like-button"]').simulate('click');
    const dispatch = useDispatch();
    expect(dispatch).toHaveBeenCalledTimes(1);
  });
});
