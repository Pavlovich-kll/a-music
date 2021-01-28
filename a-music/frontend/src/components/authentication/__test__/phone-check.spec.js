import React from "react";
import Enzyme from 'enzyme';
import {mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import CheckPhoneNumber from "../CheckPhoneNumber";
import { SignUpProvider} from "../../../HOC/withAuthContext";
import {wait} from "@testing-library/react";

Enzyme.configure({adapter: new Adapter()});

describe('Phone-check tests', () => {

    let wrapper;
    let mockHandleClick;

    beforeEach(() => {
        mockHandleClick = jest.fn();
        wrapper = mount(
            <SignUpProvider >
                <CheckPhoneNumber onContinue={mockHandleClick}/>
            </SignUpProvider>);
    });

    it("should render component with initial state", () => {
        expect(wrapper.find('input').length).toBe(1);
        expect(wrapper.find('[data-testid="submit-btn"]').find("button").length).toBe(1);
        expect(wrapper.find('.flag-dropdown').length).toBe(1);
        expect(wrapper.find('.phone-input').instance().value).toEqual('');
    });

    it('should renders property', () => {
        expect(wrapper).toMatchSnapshot()
    });

    it("should change phone-check", async () => {
        const phone = wrapper.find('[name="phone"]').find("input");
        phone.simulate("change", {
            target: {name: "phone", value: "375293192966"}
        });
        wrapper.update();
        await wait(() => {
            expect(phone.instance().value).toEqual('+375 (29) 319 29 66')
        });
    })

});