import React from "react";
import TrackList from "../TrackList";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";



Enzyme.configure({ adapter: new Adapter() });

describe("Track List tests", () => {

    let wrapper;
    let props;
    let mockHandleClick = jest.fn();
    beforeEach(() => {
        props = {
            setToPlay:mockHandleClick,
            list: [
                {
                    album: "Прятки",
                    author: "bicycles for afghanistan",
                    cover_id: "016b6645789451e0ae330295aa238410.jpg",
                    createdAt: "2020-06-01T17:23:42.750Z",
                    likes: 7,
                    title: "Саммертайм",
                    track_id: "f2dd930610c94d88ded477bbba34f146.mp3",
                    type: "music",
                    updatedAt: "2020-06-02T13:53:29.340Z",
                    __v: 0,
                    _id: "5ed5399e8660f80019671eba",
                }
            ],
            current: {}
        };
        wrapper = mount(<TrackList {...props}/>);
    });

    it('should render',  ()=> {

        const mock = 'undefinedmusic-service/content/file/';
        expect(wrapper.find('ul').length).toBeTruthy();
        expect(wrapper.find('.MuiListItem-root').length).toBeTruthy();
        expect(wrapper.find('.MuiListItemAvatar-root').length).toBeTruthy();
        expect(wrapper.find('.MuiListItemAvatar-root').find('img').prop("src")).toEqual(mock + `${props.list[0].cover_id}`);
        expect(wrapper.find('.MuiListItemText-root').length).toBeTruthy();
        expect(wrapper.find('.MuiListItemText-primary').find('span').text()).toBe(props.list[0].author);
        expect(wrapper.find('.MuiListItemText-secondary').find('p').text()).toBe(props.list[0].title);

    });

    it('should setState',  ()=> {
        wrapper.instance().handleChangeSelected('72e1b7d14dd89f6079a8d801de2338fd.mp3');
        expect(wrapper.instance().state.selectedIndex).toEqual('72e1b7d14dd89f6079a8d801de2338fd.mp3');
    });

    it('should onClick on item',  ()=> {
        wrapper.find('[role="button"]').simulate('click');
        expect(mockHandleClick).toBeCalledTimes(1);
    });

    it('should change props',  ()=> {
        const spy = jest.spyOn(TrackList.prototype, 'componentDidUpdate');
        wrapper.setProps({current: {
                album: "Futures",
                author: "Hørd",
                cover_id: "9d453ee4dfc6697409b2254b9e384dfe.jpg",
                createdAt: "2020-06-01T20:54:03.507Z",
                likes: 48,
                title: "Skin tense",
                track_id: "72e1b7d14dd89f6079a8d801de2338fd.mp3",
                type: "music",
                updatedAt: "2020-06-02T12:42:14.840Z",
                __v: 0,
                _id: "5ed56aeb8660f80019671f44"
            }});
        expect(spy).toBeCalled();
    });

});