import React from "react";
import {MainInfo} from "../MainInfo";
import Enzyme from "enzyme";
import { mount } from "enzyme";
import Adapter from "enzyme-adapter-react-16";


Enzyme.configure({ adapter: new Adapter() });

jest.mock("react-router-dom", () => ({
    useHistory: () => ({
        push: jest.fn(),
    }),
}));
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


describe('tests MainIfo component',()=> {
    const currentUser = {
        id: '1',
        username: 'Hleb',
        firstName: 'Hleb',
        lastName: 'Bulanov',
        phone: '+375293192966',
        email: 'grs@gmail.com'
    };
    
    const onChangeAvatar = jest.fn();
    
    beforeEach(()=> {
        wrapper =  mount(<MainInfo currentUser={currentUser} onChangeAvatar={onChangeAvatar}/>)
    })

    it("render correctly component", () => {
        expect(wrapper).toMatchSnapshot();
    });

    it('should ',  () => {
        wrapper.find('.avatar').first().simulate('click')
        expect(onChangeAvatar).toBeCalledTimes(1)
    });
});