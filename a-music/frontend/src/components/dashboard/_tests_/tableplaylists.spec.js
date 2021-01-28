import React from 'react';
import Enzyme, {mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import TablePlayLists from "../menu/playlists/playlists";
import {BrowserRouter} from "react-router-dom";
import {Provider} from "react-redux";
import configureMockStore from "redux-mock-store";
import thunk from "redux-thunk";

Enzyme.configure({adapter: new Adapter()});

const mockStore = configureMockStore([thunk]);

describe('TablePlayList tests', () => {
    const store = mockStore({playList: {list: [{_id: '5ee88221752a090019c2264f'}]}});
    let wrapper;
    let mockHandleClick = jest.fn();
    let props ={
        id: '5ee88221752a090019c2264f',
        handleSetId: mockHandleClick,
        getIdUpdatePlaylist: mockHandleClick,
        handleSetVisible: mockHandleClick,
        getVisibleSearchPanel: mockHandleClick,
        clearTracksInPlayList: mockHandleClick,
        handleUpdateTracks: mockHandleClick,
    };

    beforeEach(() => {
        wrapper = mount(
            <Provider store={store}>
                <BrowserRouter>
                    <TablePlayLists {...props} />
                </BrowserRouter>
            </Provider>
        )
    });

    it('should render component with initial state', () => {
        expect(
            wrapper.find('.playlist-content').length
        ).toBe(1);
        expect(
            wrapper.find('Table').length
        ).toBe(2);
    });
});
