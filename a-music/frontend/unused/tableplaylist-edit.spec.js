import React from 'react';
import Enzyme, {mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import TablePlayListEdit from "../menu/playlists/tableplaylist-edit";
import {BrowserRouter} from "react-router-dom";
import {Provider} from "react-redux";
import configureMockStore from "redux-mock-store";
import thunk from "redux-thunk";

Enzyme.configure({adapter: new Adapter()});

const mockStore = configureMockStore([thunk]);

describe('TablePlayList tests', () => {
    const store = mockStore({content: {createPlayListTracks: [{title: "test"}]}});
    let wrapper;
    let mockHandleClick = jest.fn();
    let props ={
        id: '5ee88221752a090019c2264f',
        playlistDataObj: {title: "test"},
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
                    <TablePlayListEdit {...props} />
                </BrowserRouter>
            </Provider>
        )
    });

    it('should check button-back', ()=>{
        wrapper.find('.button-back').find('button').simulate('click');
        expect(props.handleSetId).toHaveBeenCalled();
    });
    it('should check render title, description, tracks, list', ()=>{
        expect(wrapper.find('Styled(Styled(WithStyles(ForwardRef(Typography))))').length).toBe(3);
        expect(wrapper.find('List').length).toBe(1);
    });
    it('should check add tracks button', ()=>{
        wrapper.find('.button-add-tracks-test').find('button').simulate('click');
        expect(props.handleSetVisible).toHaveBeenCalled();
    });
    it('should check render search', ()=>{
        wrapper.find('.button-add-tracks-test').find('button').simulate('click');
        wrapper.update();
        expect(wrapper.find('Styled(Styled(WithStyles(ForwardRef(Typography))))').length).toBe(4);
        expect(wrapper.find('SearchPanel').length).toBe(1);
        expect(wrapper.find('.send-buttons-test').find('button').length).toBe(1);
        expect(wrapper.find('.close-buttons-test').find('button').length).toBe(1);
    });
    it('should send tracks in playlist, close search panel', ()=>{
        wrapper.find('.button-add-tracks-test').find('button').simulate('click');
        wrapper.update();
        wrapper.find('.send-buttons-test').find('button').simulate('click');
        expect(props.handleUpdateTracks).toHaveBeenCalled();
        wrapper.find('.close-buttons-test').find('button').simulate('click');
        expect(props.handleSetVisible).toHaveBeenCalled();
    });
});
