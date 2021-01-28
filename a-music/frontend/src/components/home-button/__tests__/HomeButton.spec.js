import React from "react";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import {HomeButton} from "../HomeButton";

Enzyme.configure({ adapter: new Adapter() });

let wrapper;

jest.mock('react-router-dom', () => ({
    useHistory: () => ({
        push: jest.fn(),
    }),
}));

describe('testing HomeButton component', ()=> {

    beforeEach(() => {
        wrapper = mount(<HomeButton />);
    });

    it("render correctly component", () => {
        expect(wrapper).toMatchSnapshot();
    });

});