import React from "react";
import Enzyme, {shallow, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import Player from '../player';


Enzyme.configure({ adapter: new Adapter() });

jest.mock('react-redux');
jest.mock('../../../components/current-track', () => 'div');
const play = jest
  .spyOn(window.HTMLMediaElement.prototype, 'play')
  .mockImplementation(() => {});

describe('FavoriteButton', () => {
  let wrapper;
  const useEffect = jest.spyOn(React, "useEffect").mockImplementation(f => f());
  const props = {
    onClickNext: jest.fn(),
    likes: 10,
    src: 'string',
    isPlay: false
  }

  beforeEach(() => {
    wrapper = shallow(<Player {...props} />);
  });

  it('should render', () => {
    expect(wrapper.find('[data-testid="player"]').length).toBe(1);
    expect(wrapper.find('[preload="metadata"]').length).toBe(1);
  });

  it('should run useEffect when it is rendering', () => {
    mount(<Player {...props} />);
    expect(useEffect).toHaveBeenCalled();
  });

  it('should run play when props isPlay changed to true', () => {
    const wrapper = mount(<Player {...props} />);
    wrapper.setProps({ isPlay: true });
    expect(play).toHaveBeenCalledTimes(1);
  });
});
