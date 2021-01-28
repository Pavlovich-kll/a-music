import React from "react";
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import AuthenticationButtons from '../index';
import SignUpComponent from "../../authentication/sign-up"
import ConnectAuthenticate from '../../authentication/steps/connectAuthenticate'

Enzyme.configure({adapter: new Adapter()});

describe('SingIn-SingUp Button', () => {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<AuthenticationButtons />)
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should sing in',  ()=>{
        wrapper.find('.singInButton').simulate("click");
        wrapper.update();
        expect(wrapper.find(ConnectAuthenticate).length).toBe(1);
    });

    it('should sing un',  ()=>{
        wrapper.find('.singUpButton').simulate("click");
        wrapper.update();
        expect(wrapper.find(SignUpComponent).length).toBe(1);
    });
});