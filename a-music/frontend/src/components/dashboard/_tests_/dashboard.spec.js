import React from 'react';
import Enzyme from 'enzyme';
import {mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Dashboard from '../dashboard';
import {BrowserRouter} from "react-router-dom";

Enzyme.configure({adapter: new Adapter()});

describe('Dashboard tests', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = mount(
            <BrowserRouter>
                <Dashboard/>
            </BrowserRouter>)
    });

    it('should render component with initial state', () => {
        expect(wrapper.find('Dashboard').length).toBe(1);
    });
});
