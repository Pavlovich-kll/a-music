import React from "react";
import Enzyme, {shallow} from 'enzyme';
import {mount} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Aside from "../index";

Enzyme.configure({adapter: new Adapter()});

const Test = () => (<div className={'testDiv'} />);

describe('Aside tests', () => {
    let wrapper;
    const mockHandleClock = jest.fn();

    beforeEach(() => {
        wrapper = mount(<Aside handleHideAside={mockHandleClock} hidden={true} content={Test}/>)
    });

    it('should render component with initial state',  ()=> {
        expect(wrapper.find('Aside').length).toBe(1);
        expect(wrapper.find('aside').length).toBe(1);
        expect(wrapper.find('main').length).toBe(1);
        expect(wrapper.find('.testDiv').length).toBe(1);
    });
    it('should hide button', ()=>{
        wrapper.find('Button').find('button').simulate('click');
        expect(mockHandleClock).toBeCalledTimes(1);
    });
    it('should show button', ()=>{
        wrapper = shallow(<Aside hidden={true} handleHideAside={mockHandleClock} />);
        wrapper.find('.showButton').simulate('click');
        expect(mockHandleClock).toBeCalledTimes(1);
    });
    it('should title', ()=>{
        wrapper = shallow(<Aside title={'test Title'} />);
        expect(wrapper.find('h5').text()).toEqual('test Title');
    });
    it('should function content', ()=>{
        if(typeof wrapper.prop('content') === 'function'){
            expect(wrapper.find('.testDiv').length).toBe(1);
        }
    });
});