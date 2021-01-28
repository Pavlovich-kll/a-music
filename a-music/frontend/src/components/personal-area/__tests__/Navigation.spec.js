import React from "react";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import {Navigation} from "../Navigation";


Enzyme.configure({ adapter: new Adapter() });

jest.mock('react-redux', () => ({
    useSelector: jest.fn().mockImplementation(selector => selector({
        user:  {
            current: {
                id: '1',
                username: 'Hleb',
                firstName: 'Hleb',
                lastName: 'Bulanov',
                phone: '+375293192966',
                email: 'grs@gmail.com'
            },
        }
    }))
}));
let wrapper;

describe('testings Navigation component', ()=> {

    const setState = jest.fn();
    const useStateSpy = jest.spyOn(React, 'useState')
    useStateSpy.mockImplementation((init) => [init, setState]);

    beforeEach(() => {
        wrapper = mount(<Navigation />);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it("render correctly component", () => {
        expect(wrapper).toMatchSnapshot();
    });

    it('should set state',  () => {
        wrapper.find('.MuiTab-root').first().simulate('click');
        expect(setState).toHaveBeenCalledWith('User Info');
    });

});
