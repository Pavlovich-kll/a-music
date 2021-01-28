import React from "react";
import Enzyme, {mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import Modal from "../index";


Enzyme.configure({ adapter: new Adapter() });

describe('Modal component', () => {
  let component;
  const useEffect = jest.spyOn(React, "useEffect").mockImplementation(f => f());
  const props = {
    children: <div className='test-children'>Hey Joe!</div>,
    show: true,
    onClose: jest.fn(),
    size: {
      small: '300px',
      medium: '500px',
      large: '800px'
    },
    title: 'title'
  }

  const modalRoot = global.document.createElement('div');
  modalRoot.setAttribute('id', 'modal');
  const body = global.document.querySelector('body');
  body.appendChild(modalRoot);

  beforeEach(() => {
    component = mount(
      <Modal {...props} />
    );
  });

  afterEach(() => {
    component.unmount();
  });

  it('should render with the children', () => {
    expect(component.find('[data-testid="modal-window"]').exists()).toBeTruthy();
    expect(component.find('.test-children').exists()).toBeTruthy();
  });

  it('should close the window after the outside click', () => {
    component.find('button').simulate('click');
    expect(props.onClose).toHaveBeenCalled();
  });

  it('should defined an useEffect after the mount', () => {
    expect(useEffect).toBeDefined();
  });

  it('should defined an useEffect after the unmount', () => {
    const wrapper = mount(<Modal {...props} children={<div>hey</div>}/>);
    wrapper.unmount();
    expect(useEffect).toBeDefined();
  });
});

describe('Modal component', () => {
  const props = {
    children: <div className='test-children'>Hey Joe!</div>,
    onClose: jest.fn(),
    size: {
      small: '300px',
      medium: '500px',
      large: '800px'
    },
    title: 'title'
  }

  it('should call onClose function after a click at outside of the modal window', () => {
    const outerNode = document.createElement('div');
    document.body.appendChild(outerNode);

    const wrapper = mount(<Modal {...props} />, { attachTo: outerNode });
    const toast = wrapper.find(`.modal-view-wrapper`);

    toast.instance().dispatchEvent(new Event('mousedown'));
    expect(props.onClose).not.toHaveBeenCalled();

    outerNode.dispatchEvent(new Event('mousedown'));
    expect(props.onClose).toHaveBeenCalled();
  });
})
