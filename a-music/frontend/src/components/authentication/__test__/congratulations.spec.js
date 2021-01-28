import React from "react";
import Enzyme from 'enzyme';
import {shallow, mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Congratulations from "../Congratulations";


Enzyme.configure({ adapter: new Adapter() });

describe('Congratulations tests', ()=> {
    let wrapper;
    let props;
    const mockHandleClick = jest.fn();

    beforeEach(() => {
        props = {
            onContinue: mockHandleClick
        };
        wrapper = mount(<Congratulations {...props}/>);
    });

    it('should render component with all element',  ()=> {
        expect(wrapper.find('img').length).toBe(1);
        expect(wrapper.find('h5').length).toBe(1);
        expect(wrapper.find('button').length).toBe(1);
    });

    it('should checking call after click on button', ()=> {
        wrapper.find('button').simulate('click');
        expect(mockHandleClick).toHaveBeenCalledTimes(1);
    });

    it('should checking call after click on img',()=> {
        const mockHandleClick = jest.fn();
        const props = {
            onContinue: mockHandleClick
        };
        const wrapper = shallow(<Congratulations {...props}/>);
        wrapper.find('img').simulate('click');
        expect(mockHandleClick).toHaveBeenCalledTimes(0);
    });

    it('should checking call after click on h5',()=> {
        const mockHandleClick = jest.fn();
        const props = {
            onContinue: mockHandleClick
        };
        const wrapper = shallow(<Congratulations {...props}/>);
        wrapper.find('h5').simulate('click');
        expect(mockHandleClick).toHaveBeenCalledTimes(0);
    });
});
