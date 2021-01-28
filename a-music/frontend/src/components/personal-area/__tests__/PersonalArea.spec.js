import React from "react";
import PersonalArea from "../PersonalArea";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";


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

jest.mock("react-router-dom", () => ({
    useHistory: () => ({
        push: jest.fn(),
    }),
}));

let wrapper;


describe('PersonalArea tests', ()=> {

    const setState = jest.fn();
    const useStateSpy = jest.spyOn(React, 'useState')
    useStateSpy.mockImplementation((init) => [init, setState]);

    beforeEach(() => {
        wrapper = mount(<PersonalArea />);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it("render correctly component", () => {
        expect(wrapper).toMatchSnapshot();
    });


});




