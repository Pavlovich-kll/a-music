import React from "react";
import AvatarEditor from "../modal-components/AvatarEditor";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import {wait} from "@testing-library/react";

Enzyme.configure({ adapter: new Adapter() });

describe('tests AvatarEditor component', () => {
    let wrapper;
    let mockOnClose = jest.fn();

    const setState = jest.fn();
    const useStateSpy = jest.spyOn(React, 'useState')
     useStateSpy.mockImplementation(() => ['someAvatar.jpeg', setState]);

    beforeEach(()=> {
        wrapper = mount(<AvatarEditor onClose={mockOnClose}/>)
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it("render correctly component", () => {
        expect(wrapper).toMatchSnapshot();
    });

    it('should after add picture avatar',  () => {
        expect(wrapper.find('.status-input').text()).toEqual('someAvatar.jpeg');
    });

    it('should set state',  async () => {

        wrapper.find('input').simulate('change', {
            target: {name: 'avatar', value:'someAvatar.jpeg'}
        });
        wrapper.find("form").simulate("submit");
        wrapper.update();
        await wait(() => {
            expect(setState).toHaveBeenCalledWith('someAvatar.jpeg');
        });
    });

    it('should click submit button',   async () => {

        wrapper.find('input').simulate('change', {
            target: {name: 'avatar', value:'someAvatar.jpeg'}
        });
        wrapper.find("form").simulate("submit");
        wrapper.update();

        await wait(() => {
            expect(mockOnClose).toBeCalledTimes(1)
        });
    });

});