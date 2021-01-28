import React from "react";
import Enzyme from 'enzyme';
import {mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import { SignUpProvider} from "../../../HOC/withAuthContext";
import {wait} from "@testing-library/react";
import Registration from "../Registration";


Enzyme.configure({adapter: new Adapter()})

jest.mock("react-router-dom", () => ({
    useHistory: () => ({
      push: jest.fn(),
    }),
  }));

describe('Registration-form tests', () => {
    let wrapper;
    let mockHandleClick;
    let props;

    beforeEach(() => {
        jest.clearAllMocks()
        mockHandleClick = jest.fn()            
        wrapper = mount(<SignUpProvider><Registration onContinue={mockHandleClick} {...props} /></SignUpProvider>);        
    });

    it('should render all the elements', () => {
        expect(wrapper.find('input').find('[name="username"]')).toHaveLength(1)
        expect(wrapper.find('input').find('[name="firstName"]')).toHaveLength(1)
        expect(wrapper.find('input').find('[name="lastName"]')).toHaveLength(1)
        expect(wrapper.find('input').find('[name="email"]')).toHaveLength(1)
        expect(wrapper.find('input').find('#password')).toHaveLength(1)
        expect(wrapper.find('input').find('#repeatPassword')).toHaveLength(1)
        expect(wrapper.find('button').find('[type="submit"]')).toHaveLength(1)
    })

    it('should render properly', () => {
        expect(wrapper).toMatchSnapshot()
    });

    it('should render error messages', () => {
        expect(wrapper.find('[name="username"]').find('.error').length).toBeTruthy()
        expect(wrapper.find('[name="firstName"]').find('.error').length).toBeTruthy()
        expect(wrapper.find('[name="lastName"]').find('.error').length).toBeTruthy()
        expect(wrapper.find('[name="email"]').find('.error').length).toBeTruthy()
        expect(wrapper.find('[name="password"]').find('.error').length).toBeTruthy()
        expect(wrapper.find('[name="repeatPassword"]').find('.error').length).toBeTruthy()
    });

    it('should return username input value', async () => {
        const username = wrapper.find('[name="username"]').find('input');
        username.simulate('change', {
            target: {name: 'username', value: 'test_username'}
        });
        wrapper.update();
        await wait(() => {
            expect(username.instance().value).toEqual('test_username')
        })
    });

    it('should return firstName input value', async () => {
        const firstName = wrapper.find('[name="firstName"]').find('input');
        firstName.simulate('change', {
            target: {name: 'firstName', value: 'test_firstName'}
        });
        wrapper.update();
        await wait(() => {
            expect(firstName.instance().value).toEqual('test_firstName')
        })
    });

    it('should return lastname input value', async () => {
        const lastName = wrapper.find('[name="lastName"]').find('input');
        lastName.simulate('change', {
            target: {name: 'lastName', value: 'test_lastName'}
        });
        wrapper.update();
        await wait(() => {
            expect(lastName.instance().value).toEqual('test_lastName')
        })
    });

    it('should return email input value', async () => {
        const email = wrapper.find('[name="email"]').find('input');
        email.simulate('change', {
            target: {name: 'email', value: 'test_email'}
        });
        wrapper.update();
        await wait(() => {
            expect(email.instance().value).toEqual('test_email')
        })
    });

    it('should return password input value', async () => {
        const password = wrapper.find('[name="password"]').find('input');
        password.simulate('change', {
            target: {name: 'password', value: 'test_password'}
        });
        wrapper.update();
        await wait(() => {
            expect(password.instance().value).toEqual('test_password')
        })
    });

    it('should return repeatPassword input value', async () => {
        const repeatPassword = wrapper.find('[name="repeatPassword"]').find('input');
        repeatPassword.simulate('change', {
            target: {name: 'repeatPassword', value: 'test_password'}
        });
        wrapper.update();
        await wait(() => {
            expect(repeatPassword.instance().value).toEqual('test_password')
        })
    });

    it('should call function on submit', async () => {
        wrapper.find('[name="username"]').find('input').simulate('change', {
            target: {name: 'username', value: 'testUserName'}
        });

        wrapper.find('[name="firstName"]').find('input').simulate('change', {
            target: {name: 'firstName', value: 'testName'}
        });

        wrapper.find('[name="lastName"]').find('input').simulate('change', {
            target: {name: 'lastName', value: 'testLastName'}
        });

        wrapper.find('[name="email"]').find('input').simulate('change', {
            target: {name: 'email', value: 'test@gmail.com'}
        });

        wrapper.find('[name="password"]').find('input').simulate('change', {
            target: {name: 'password', value: '24242424'}
        });

        wrapper.find('[name="repeatPassword"]').find('input').simulate('change', {
            target: {name: 'repeatPassword', value: '24242424'}
        });

        wrapper.find('form').simulate('submit');
        wrapper.update();
        await wait(() => {
            expect(mockHandleClick).toHaveBeenCalledTimes(1)
        });         
    })

});
    