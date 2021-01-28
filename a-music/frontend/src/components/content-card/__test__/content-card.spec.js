import React from 'react'
import Card from '../card'
import { mount } from 'enzyme'

const mockDispatch = jest.fn()
jest.mock('react-redux', () => ({
  useSelector: jest.fn(),
  useDispatch: () => mockDispatch,
}))

describe('Track card test', () => {
  const mockHandleClick = jest.fn()
  let wrapper

  const props = {
    onAddToFavorite: mockHandleClick,
    onRemoveFromFavorite: mockHandleClick,
    handleTogglePlay: mockHandleClick,
    trackData: {},
  }

  beforeEach(() => {
    wrapper = mount(<Card {...props} />)
  })

  test('should render correctly', () => {
    expect(wrapper).toMatchSnapshot()
  })
  test('should call add function when add-button is clicked', () => {
    wrapper.find('[data-testid="add-track"]').find('button').simulate('click')
    expect(mockHandleClick).toHaveBeenCalled()
  })

  test('should call add function when addButton is clicked', () => {
    wrapper.find('[data-testid="toggle-play"]').find('button').simulate('click')
    expect(mockHandleClick).toHaveBeenCalled()
  })

  test('should call remove function when remove-button is clicked', () => {
    wrapper.find('[data-testid="remove-track"]').find('button').simulate('click')
    expect(mockHandleClick).toHaveBeenCalled()
  })
})
