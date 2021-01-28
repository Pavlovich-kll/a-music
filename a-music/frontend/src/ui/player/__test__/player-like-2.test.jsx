import thunk from "redux-thunk";
import Enzyme, {mount} from "enzyme";
import {Provider} from "react-redux";
import React from "react";
import {LikeButton} from "../player-like";
import Adapter from "enzyme-adapter-react-16";
import configureStore from 'redux-mock-store'
import {setLikeToTrack} from "../../../actions/content";


Enzyme.configure({ adapter: new Adapter() });

describe('FavoriteButton', () => {
  const mockStore = configureStore([thunk]);
  const store = mockStore({ content: { current: { track_id: 'track-id' }}});

  store.dispatch = jest.fn();

  const wrapper = mount(
    <Provider store={store}>
      <LikeButton likes={10} />
    </Provider>
  );

  it('should dispatch an action setLikeToTrack one time', () => {
    wrapper.find('[data-testid="like-button"]').at(0).simulate('click');
    expect(store.dispatch).toHaveBeenCalledTimes(1);
    // I need your help with code below!!! For some reason the test failed with this string!!!
    // expect(store.dispatch).toHaveBeenCalledWith(setLikeToTrack('track-id')); // !!! Fail the test!!!
    expect(store.dispatch.mock.calls[0].toString()).toBe(setLikeToTrack('track-id').toString());
  });
});
