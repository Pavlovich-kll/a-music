import configureMockStore from "redux-mock-store";
import thunk from "redux-thunk";
import {mount} from "enzyme";
import {Provider} from "react-redux";
import {FavoriteButton} from "../player-favorite";
import React from "react";

describe('FavoriteButton', () => {
  const mockStore = configureMockStore([thunk]);

  it('should render a startup component if startup is not complete', () => {
    localStorage.setItem('favoriteTracks', JSON.stringify(['string']));
    const store = mockStore({ content: { current: { id: 'string' }}});
    const wrapper = mount(
      <Provider store={store}>
        <FavoriteButton />
      </Provider>
    );
    expect(wrapper.find('[data-testid="star-icon"]').exists()).toBeTruthy();
  });
});
