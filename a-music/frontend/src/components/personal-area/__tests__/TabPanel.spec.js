import React from "react";
import {TabPanel} from "../TabPanel";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";


Enzyme.configure({ adapter: new Adapter() });

let wrapper;
describe('tests TabPane components', () => {

    beforeEach(()=> {
        wrapper = mount(<TabPanel />)
    });

    it("render correctly component", () => {
        expect(wrapper).toMatchSnapshot();
    });
});